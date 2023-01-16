package com.hejz.nettyserver;

import com.hejz.common.Constant;
import com.hejz.entity.Relay;
import com.hejz.entity.RelayDefinitionCommand;
import com.hejz.entity.Sensor;
import com.hejz.service.RelayDefinitionCommandService;
import com.hejz.service.RelayService;
import com.hejz.utils.HexConvert;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-14 09:45
 * @Description: 处理继电器指令——含服务器主动发出的指令和接到指令的处理
 */
@Component
@Slf4j
public class ProcessRelayCommands {
    @Autowired
    private RelayService relayService;
    @Autowired
    private RelayDefinitionCommandService relayDefinitionCommandService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 传感器数据转为字符串——没有imei值的有效数据
     *
     * @param bytes 收到byte[]信息
     * @return
     */
    String sensorDataToString(byte[] bytes) {
        //截取有效值进行分析——不要imei值
        int useLength = bytes.length - Constant.IMEI_LENGTH;
        byte[] useBytes = NettyServiceCommon.getUseBytes(bytes, useLength);
        return HexConvert.BinaryToHexString(useBytes).trim();
    }

    /**
     * 继电器指令返回数据处理
     *
     * @param relays
     * @param relayDefinitionCommands
     * @return
     */
    List<String> getSendHex(List<Relay> relays, LinkedHashSet<RelayDefinitionCommand> relayDefinitionCommands) {
        List<String> relayIds = relayDefinitionCommands.stream().map(RelayDefinitionCommand::getRelayIds).collect(Collectors.toList());
        List<String> relayIdList = new ArrayList<>();
        for (String relayId : relayIds) {
            relayIdList.addAll(Arrays.asList(relayId.split(",")));
        }
        List result = new ArrayList();
        for (String s : relayIdList) {
            String[] strings = s.split("-");
            List<String> list = relays.stream().filter(r -> r.getId().equals(Long.valueOf(strings[0])))
                    .map(strings[1].equals("1") ? Relay::getOpneHex : Relay::getCloseHex)
                    .collect(Collectors.toList());
            result.addAll(list);
        }
        return result;
    }

    /**
     * 处理继电器返回值
     *
     * @param ctx
     * @param bytes
     */
    void start(ChannelHandlerContext ctx, byte[] bytes) {
        String imei = NettyServiceCommon.calculationImei(bytes);
        //把数据bytes转化为string
        String useData = sensorDataToString(bytes);
        log.info("通道：{} imei={}  继电器返回值：{}", ctx.channel().id().toString(), imei, useData);
        if (!NettyServiceCommon.testingData(bytes)) {
            log.error("继电器返回值：{}校验不通过！", HexConvert.BinaryToHexString(bytes));
            return;
        }
        //从缓存中取指令
        String key = Constant.CACHE_INSTRUCTIONS_THAT_NEED_TO_CONTINUE_PROCESSING_CACHE_KEY + "::" + ctx.channel().id().toString() + "::" + useData;
        Object o = redisTemplate.opsForValue().get(key);
        if (o == null) return;
        RelayDefinitionCommand relayDefinitionCommand = (RelayDefinitionCommand) o;
        Long commonId = relayDefinitionCommand.getCommonId();
        //把要重新处理的relayDefinitionCommand再给原来的relayDefinitionCommand
        RelayDefinitionCommand relayDefinitionCommand1 = relayDefinitionCommandService.getByImei(imei).stream()
                .filter(r -> r.getId().equals(commonId)).findFirst().get();
        //把要重新处理的relayDefinitionCommand再给原来的relayDefinitionCommand——BeanUtils.copyProperties防止jpa程序报错
        ctx.channel().eventLoop().schedule(() -> {
            log.info("通道==》{}开始延时任务，延时：{}", ctx.channel().id().toString(), relayDefinitionCommand1.getProcessingWaitingTime());
            sendRelayCommandAccordingToLayIds(ctx, relayDefinitionCommand1);
        }, relayDefinitionCommand.getProcessingWaitingTime(), TimeUnit.MILLISECONDS);
    }

    /**
     * 根据数据处理继电器指令处理
     *
     * @param sensor 串号
     * @param ids    继电器指令——奇数表示对应的继电器命令的ids，偶数：1表示为使用闭合指令，0表示为断开指令
     * @param ctx    通道上下文
     */
    void relayCommandData(Sensor sensor, Long ids, ChannelHandlerContext ctx) {
        if (ids == null || ids.equals("0")) return;
        //编辑继电器指令
        Optional<RelayDefinitionCommand> first = relayDefinitionCommandService.getByImei(sensor.getImei()).stream().filter(relayDefinitionCommand -> relayDefinitionCommand.getId().equals(ids)).findFirst();
        if (!first.isPresent()) return;
        RelayDefinitionCommand relayDefinitionCommand = first.get();
        String relayIds = relayDefinitionCommand.getRelayIds();
        //指令奇数表示对应的继电器id，偶数：1表示为使用闭合指令，0表示为断开指令
        String[] relayIdsArr = relayIds.split(",");
        for (String s : relayIdsArr) {
            //根据layIds发送继电器指令
            sendRelayCommandAccordingToLayIds(ctx, relayDefinitionCommand);

        }
    }

    /**
     * 根据layIds发送继电器指令
     *
     * @param ctx
     * @param relayDefinitionCommand
     */
    private void sendRelayCommandAccordingToLayIds(ChannelHandlerContext ctx, RelayDefinitionCommand relayDefinitionCommand) {
        String[] r = relayDefinitionCommand.getRelayIds().split(",");
        List<Relay> relayList = relayService.getByImei(relayDefinitionCommand.getImei());
        for (String s : r) {
            String[] s1 = s.split("-");
            loop:
            for (Relay relay : relayList) {
                if (String.valueOf(relay.getId()).equals(s1[0])) {
                    String sendHex = s1[1].equals("1") ? relay.getOpneHex() : relay.getCloseHex();
                    //缓存需要继续处理的指令，如果不再处理不缓存——为程序收到继电器信号（继电器发送什么信号接收到什么信号）能联系在一起
                    if (relayDefinitionCommand.getIsProcessTheReturnValue()) {
                        cacheInstructionsThatNeedToContinueProcessing(ctx, sendHex, relayDefinitionCommand);
                    }
                    NettyServiceCommon.write(sendHex, ctx);
                    // TODO: 2023/1/4 处理url发出指令
                    break loop;
                }
            }
        }

    }

    /**
     * 缓存需要继续处理的指令，如果不再处理不缓存——为程序收到继电器信号（继电器发送什么信号接收到什么信号）能联系在一起
     *
     * @param ctx
     * @param sendHex
     * @param relayDefinitionCommand
     */
    private void cacheInstructionsThatNeedToContinueProcessing(ChannelHandlerContext ctx, String sendHex, RelayDefinitionCommand relayDefinitionCommand) {
        //设置10分钟
        redisTemplate.opsForValue().set(Constant.CACHE_INSTRUCTIONS_THAT_NEED_TO_CONTINUE_PROCESSING_CACHE_KEY + "::" + ctx.channel().id() + "::" + sendHex, relayDefinitionCommand, Duration.ofMillis(Constant.EXPIRATION_TIME_OF_CACHE_INSTRUCTIONS_THAT_NEED_TO_CONTINUE_PROCESSING_CACHE_KEYS));
    }

    /**
     * 根据继电器指令处理
     *
     * @param sensor 感应器指令
     * @param data   测试结果值
     * @param ctx    通道上下文
     */
    public void handleAccordingToRelayCommand(Sensor sensor, double data, ChannelHandlerContext ctx) {
        Long id = null;
        //根据结果值判断是否处理
        if (data - Double.parseDouble(sensor.getMax().toString()) > 0) {
            log.info("{} 结果值 {} 大于最大值 {}", sensor.getName(), data, sensor.getMax());
            String key = ctx.channel().id().toString()+"max" + sensor.getId();
            if (Constant.THREE_RECORDS_MAP.get(key) == null) {
                List<Double> l = new ArrayList<>();
                l.add(data);
                Constant.THREE_RECORDS_MAP.put(key, l);
            } else {
                List<Double> list = Constant.THREE_RECORDS_MAP.get(key);
                list.add(data);
                Constant.THREE_RECORDS_MAP.put(key, list);
            }
            if (Constant.THREE_RECORDS_MAP.get(key).size() == 3) {
                List<Double> collect = Constant.THREE_RECORDS_MAP.get(key).stream().sorted().collect(Collectors.toList());
                if (collect.get(2) - Double.parseDouble(sensor.getMax().toString()) > 0) {
                    id = sensor.getMaxRelayDefinitionCommandId();
                    relayCommandData(sensor, id, ctx);
                    Constant.THREE_RECORDS_MAP.remove(key);
                }
            }
        } else if (data - Double.parseDouble(sensor.getMin().toString()) < 0) {
            log.info("{} 结果值 {} 小于最小值{}", sensor.getName(), data, sensor.getMin());
            String key = ctx.channel().id().toString()+"min" + sensor.getId();
            if (Constant.THREE_RECORDS_MAP.get(key) == null) {
                List<Double> l = new ArrayList<>();
                l.add(data);
                Constant.THREE_RECORDS_MAP.put(key, l);
            } else {
                List<Double> list = Constant.THREE_RECORDS_MAP.get(key);
                list.add(data);
                Constant.THREE_RECORDS_MAP.put(key, list);
            }
            if (Constant.THREE_RECORDS_MAP.get(key).size() == 3) {
                List<Double> collect = Constant.THREE_RECORDS_MAP.get(key).stream().sorted().collect(Collectors.toList());
                if (collect.get(2) - Double.parseDouble(sensor.getMin().toString()) < 0) {
                    id = sensor.getMaxRelayDefinitionCommandId();
                    relayCommandData(sensor, id, ctx);
                    Constant.THREE_RECORDS_MAP.remove(key);
                }
            }
        } else {
            log.info("{} 结果值 {} 比较合理，不用处理！", sensor.getName(), data);
        }
    }
}

