package com.hejz.dtu.nettyserver;

import com.hejz.dtu.common.Constant;
import com.hejz.dtu.enm.InstructionTypeEnum;
import com.hejz.dtu.entity.CommandStatus;
import com.hejz.dtu.entity.DtuInfo;
import com.hejz.dtu.entity.InstructionDefinition;
import com.hejz.dtu.entity.Sensor;
import com.hejz.dtu.service.CommandStatusService;
import com.hejz.dtu.service.DtuInfoService;
import com.hejz.dtu.service.InstructionDefinitionService;
import com.hejz.dtu.utils.HexConvert;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private InstructionDefinitionService instructionDefinitionService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private CommandStatusService commandStatusService;
    @Autowired
    private DtuInfoService dtuInfoService;

    /**
     * 传感器数据转为字符串——没有imei值的有效数据
     *
     * @param bytes 收到byte[]信息
     * @return
     */
    String sensorDataToString(byte[] bytes) {
        //截取有效值进行分析——不要imei值
        return HexConvert.BinaryToHexString(bytes).trim();
    }

    /**
     * 处理继电器返回值
     *
     * @param ctx
     * @param bytes
     */
    void start(ChannelHandlerContext ctx, byte[] bytes) {
        AttributeKey<Long> dtuKey = AttributeKey.valueOf(Constant.CHANNEl_KEY);
        Long dtuId = ctx.channel().attr(dtuKey).get();
        DtuInfo dtuInfo = dtuInfoService.findById(dtuId);
        //把数据bytes转化为string
        String useData = sensorDataToString(bytes);
        log.info("通道：{} dtuId={}  继电器返回值：{}", ctx.channel().id().toString(), dtuInfo.getId(), useData);
        if (!NettyServiceCommon.testingData(bytes, ctx.channel())) {
            log.error("继电器返回值：{}校验不通过！", HexConvert.BinaryToHexString(bytes));
            return;
        }
        //从缓存中取指令
        String key = Constant.CACHE_INSTRUCTIONS_THAT_NEED_TO_CONTINUE_PROCESSING_CACHE_KEY + "::" + ctx.channel().id().toString() + "::" + useData;
        Object o = redisTemplate.opsForValue().get(key);
        if (o == null) return;
        InstructionDefinition instructionDefinition = (InstructionDefinition) o;
        //处理过对应id锁:的不需要再次处理--有可能出现重复情况
        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(Constant.PROCESSED_THE_CORRESPONDING_ID_LOCK + "::" + ctx.channel().id().toString() + "::" + instructionDefinition.getId(), "1", Duration.ofSeconds(5L));
        if (!aBoolean) return;
        //添加修改命令状态
        List<InstructionDefinition> list = instructionDefinitionService.findAllByDtuId(dtuId);
        list.stream().forEach(instructionDefinition1 -> instructionDefinition1.getCommands().stream().forEach(command -> {
            //把其相反的状态置为false——过去时的
            InstructionDefinition oppositeInstructionDefinition = oppositeState(dtuInfo, instructionDefinition);
            CommandStatus commandStatus = commandStatusService.findByInstructionDefinition(oppositeInstructionDefinition);
            commandStatus.setStatus(false);
            commandStatus.setUpdateDate(new Date());
            commandStatusService.save(commandStatus);
        }));
        //保存当前状态
        commandStatusService.save(new CommandStatus(dtuInfo, instructionDefinition, new Date(), new Date(), true));
        //处理下一级指令
        NettyServiceCommon.sendRelayCommandAccordingToLayIds(instructionDefinition);
    }

    /**
     * 计算出相反的指令
     *
     * @param dtuInfo
     * @param instructionDefinition
     * @return
     */
    private InstructionDefinition oppositeState(DtuInfo dtuInfo, InstructionDefinition instructionDefinition) {
        InstructionTypeEnum typeEnum;
        int ordinal = instructionDefinition.getInstructionType().ordinal();
        if (ordinal % 2 == 0) {
            typeEnum = InstructionTypeEnum.values()[ordinal + 1];
        } else {
            typeEnum = InstructionTypeEnum.values()[ordinal - 1];
        }
        return instructionDefinitionService.findByDtuInfoAndInstructionType(dtuInfo, typeEnum);
    }

    /**
     * 根据数据处理继电器指令处理
     *
     * @param instructionDefinition     继电器指令——奇数表示对应的继电器命令的id
     */
    void relayCommandData(InstructionDefinition instructionDefinition) {
        //编辑继电器指令
        CommandStatus commandStatuses = commandStatusService.findByInstructionDefinition(instructionDefinition);
        //判断当前状态是否一致，如果一致则不往继电器发送状态了；
        if (commandStatuses != null) {
            //如果状态存在，就不发送命令了
            log.info("dtuId:{} 当前状态已经存在，不需要重复发送指令：{}", instructionDefinition.getDtuInfo().getId(), instructionDefinition);
            return;
        }
        NettyServiceCommon.sendRelayCommandAccordingToLayIds(instructionDefinition);
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
            String key = ctx.channel().id().toString() + "max" + sensor.getId();
            if (Constant.THREE_RECORDS_MAP.get(key) == null) {
                List<Double> l = new ArrayList<>();
                l.add(data);
                Constant.THREE_RECORDS_MAP.put(key, l);
            } else {
                List<Double> list = Constant.THREE_RECORDS_MAP.get(key);
                list.add(data);
                Constant.THREE_RECORDS_MAP.put(key, list);
            }
            //校验次
            if (Constant.THREE_RECORDS_MAP.get(key).size() == 3) {
                List<Double> collect = Constant.THREE_RECORDS_MAP.get(key).stream().sorted().collect(Collectors.toList());
                if (collect.get(2) - Double.parseDouble(sensor.getMax().toString()) > 0) {
                    InstructionDefinition maxInstructionDefinitionId = sensor.getMaxInstructionDefinitionId();
                    relayCommandData( maxInstructionDefinitionId);
                    Constant.THREE_RECORDS_MAP.remove(key);
                }
            }
        } else if (data - Double.parseDouble(sensor.getMin().toString()) < 0) {
            log.info("{} 结果值 {} 小于最小值{}", sensor.getName(), data, sensor.getMin());
            String key = ctx.channel().id().toString() + "min" + sensor.getId();
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
                    InstructionDefinition minInstructionDefinitionId = sensor.getMinInstructionDefinitionId();
                    relayCommandData(minInstructionDefinitionId);
                    Constant.THREE_RECORDS_MAP.remove(key);
                }
            }
        } else {
            log.info("{} 结果值 {} 比较合理，不用处理！", sensor.getName(), data);
        }
    }
}

