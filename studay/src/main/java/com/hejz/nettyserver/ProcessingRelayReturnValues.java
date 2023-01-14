package com.hejz.nettyserver;

import com.hejz.common.Constant;
import com.hejz.entity.Relay;
import com.hejz.entity.RelayDefinitionCommand;
import com.hejz.repository.CommandStatusRepository;
import com.hejz.service.RelayDefinitionCommandService;
import com.hejz.service.RelayService;
import com.hejz.utils.HexConvert;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-14 09:45
 * @Description: 处理继电器返回值
 */
@Component
@Slf4j
public class ProcessingRelayReturnValues {
    @Autowired
    private RelayService relayService;
    @Autowired
    private RelayDefinitionCommandService relayDefinitionCommandService;

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
     *  @param ctx
     * @param bytes
     */
    void start(ChannelHandlerContext ctx, byte[] bytes) {
        String imei = NettyServiceCommon.calculationImei(bytes);
        //把数据bytes转化为string
        String useData = sensorDataToString(bytes);
        log.info("通道：{} imei={}  继电器返回值：{}", ctx.channel().id().toString(), imei, useData);
        if (!NettyServiceCommon.testingData(bytes)) {
            log.error("继电器返回值：{}校验不通过！", HexConvert.BinaryToHexString(bytes));
        }
        //只检查闭合的接收数据，不检查断开的接收数据
        //查询机电器指令与之相配
        Optional<Relay> relayOptional = relayService.getByImei(imei).stream().filter(relay -> relay.getOpneHex().equals(useData) || relay.getCloseHex().equals(useData)).findFirst();
        if (!relayOptional.isPresent()) {
            log.error("查询不到继电器的命令imei:{} useData：{}",imei,useData);
            return;
        }
        int hexStatus = 0;
        if (relayOptional.get().getOpneHex().equals(useData)) hexStatus = 1;
        String ids = relayOptional.get().getId() + "-" + hexStatus;
        List<RelayDefinitionCommand> definitionCommandList = relayDefinitionCommandService.getByImei(imei);
        //查询是否需要对其命令是否进行再次操作——防止循环执行命令
        List<RelayDefinitionCommand> relayDefinitionCommands = definitionCommandList.stream().filter(r -> r.getRelayIds().indexOf(ids) >= 0 && r.getIsProcessTheReturnValue()).collect(Collectors.toList());
        if (relayDefinitionCommands.isEmpty()) {
            return;
        }
        LinkedHashSet<RelayDefinitionCommand> relayDefinitionCommandList = new LinkedHashSet<>();
        for (RelayDefinitionCommand relayDefinitionCommand : relayDefinitionCommands) {
            Optional<RelayDefinitionCommand> first = definitionCommandList.stream()
                    .filter(r -> r.getId().equals(relayDefinitionCommand.getCommonId())).findFirst();
            if (first.isPresent()) relayDefinitionCommandList.add(first.get());
        }
        List<String> sendHex = getSendHex(relayService.getByImei(imei), relayDefinitionCommandList);
        try {
            Thread.sleep(relayDefinitionCommands.get(0).getProcessTheReturnValueTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (String hex : sendHex) {
            NettyServiceCommon.write(hex, ctx);
        }
    }
}
