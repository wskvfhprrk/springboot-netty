package com.hejz.dtu.nettyserver;

import com.hejz.dtu.common.Constant;
import com.hejz.dtu.entity.DtuInfo;
import com.hejz.dtu.entity.InstructionDefinition;
import com.hejz.dtu.entity.Sensor;
import com.hejz.dtu.service.DtuInfoService;
import com.hejz.dtu.utils.HexConvert;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
    }

    /**
     * 根据继电器指令处理
     *
     * @param sensor 感应器指令
     * @param data   测试结果值
     * @param ctx    通道上下文
     */
    public void handleAccordingToRelayCommand(Sensor sensor, double data, ChannelHandlerContext ctx) {

        if (data - Double.parseDouble(sensor.getMax().toString()) > 0) {
            if(sensor.getMaxInstructionDefinitionId()==null)return;
            log.info("{} 结果值 {} 大于最大值 {}", sensor.getName(), data, sensor.getMax());
            String key = "max" + sensor.getDtuInfo().getId();
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
                    if (maxInstructionDefinitionId != null) {
                        NettyServiceCommon.sendRelayCommandAccordingToLayIds(maxInstructionDefinitionId);
                    }
                    Constant.THREE_RECORDS_MAP.remove(key);
                }
            }
        } else if (data - Double.parseDouble(sensor.getMin().toString()) < 0) {
            if(sensor.getMinInstructionDefinitionId()==null)return;
            log.info("{} 结果值 {} 小于最小值{}", sensor.getName(), data, sensor.getMin());
            String key = "min" + sensor.getDtuInfo().getId();
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
                    if (minInstructionDefinitionId != null) {NettyServiceCommon.sendRelayCommandAccordingToLayIds(minInstructionDefinitionId);
                    }
                    Constant.THREE_RECORDS_MAP.remove(key);
                }
            }
        } else {
            log.info("{} 结果值 {} 比较合理，不用处理！", sensor.getName(), data);
        }
    }
}

