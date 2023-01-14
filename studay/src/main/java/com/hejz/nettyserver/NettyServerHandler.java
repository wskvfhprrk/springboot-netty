package com.hejz.nettyserver;

import com.hejz.common.Constant;
import com.hejz.entity.*;
import com.hejz.service.*;
import com.hejz.utils.HexConvert;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@ChannelHandler.Sharable
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler {

    @Autowired
    RelayService relayService;
    @Autowired
    SensorService sensorService;
    @Autowired
    DtuInfoService dtuInfoService;
    @Autowired
    RelayDefinitionCommandService relayDefinitionCommandService;
    @Autowired
    CheckingRulesService checkingRulesService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    DtuRegister dtuRegister;
    @Autowired
    ProcessDtuPollingReturnValue processDtuPollingReturnValue;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // 获取当前连接的客户端的 channel
        Channel incoming = ctx.channel();
        // 将客户端的 Channel 存入 ChannelGroup 列表中
        Constant.CHANNEL_GROUP.add(incoming);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //客户端定时发送空包
        scheduleSendHeartBeat(ctx);
    }

    private void scheduleSendHeartBeat(ChannelHandlerContext ctx) {
        ctx.executor().schedule(() -> {
            if (ctx.channel().isActive()) {
                //发送空包（定义一个实体）
                ByteBuf bufff = Unpooled.buffer();
                //对接需要16进制的byte[],不需要16进制字符串有空格
                log.info("向通道：{}发送了心跳包数据：00 00",ctx.channel().id().toString());
                bufff.writeBytes(HexConvert.hexStringToBytes("0000"));
                ctx.writeAndFlush(bufff);
            }
        }, Constant.INTERVAL_TIME, TimeUnit.SECONDS);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.getCause();
        ctx.channel().close();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) {
        Channel channel = ctx.channel();
        Constant.CHANNEL_GROUP.forEach(channel1 -> {
            if (channel1 == channel) {//匹配当前连接对象
                start(ctx, (ByteBuf) msg);
            }
        });

    }

    private void start(ChannelHandlerContext ctx, ByteBuf msg) {
        //当前数据个数
        ByteBuf byteBuf = msg;
        //获取缓冲区可读字节数
        int readableBytes = byteBuf.readableBytes();
        byte[] bytes = new byte[readableBytes];
        byteBuf.readBytes(bytes);
        //dtu必须开通注册功能，开通注册才可以查询到信息
        if (readableBytes == Constant.DUT_REGISTERED_BYTES_LENGTH) {
            dtuRegister.start(bytes, ctx);
        } else if (readableBytes == (Constant.DTU_POLLING_RETURN_LENGTH)) { //处理dtu轮询返回值
            processDtuPollingReturnValue.start(ctx, bytes);
        } else if (readableBytes == (Constant.RELAY_RETURN_VALUES_LENGTH)) { //处理继电器返回值
            new Thread(() -> {
                processingRelayReturnValues(ctx, bytes);
            }).start();
        } else {
            log.error("通道：{},获取的byte[]长度： {} ，不能解析数据,server received message：{}",ctx.channel().id(), readableBytes, HexConvert.BinaryToHexString(bytes));
        }
    }

    /**
     * 处理继电器返回值
     *
     * @param ctx
     * @param bytes
     */
    private void processingRelayReturnValues(ChannelHandlerContext ctx, byte[] bytes) {
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
        List<RelayDefinitionCommand> relayDefinitionCommands = relayDefinitionCommandService.getByImei(imei).stream().filter(r -> r.getRelayIds().indexOf(ids) >= 0 && r.getIsProcessTheReturnValue()).collect(Collectors.toList());
        if (relayDefinitionCommands.isEmpty()) {
            log.error("查询不到继电器定义命令imei:{},ids：{}",imei,ids);
            return;
        }
        LinkedHashSet<RelayDefinitionCommand> relayDefinitionCommandList = new LinkedHashSet<>();
        for (RelayDefinitionCommand relayDefinitionCommand : relayDefinitionCommands) {
            Optional<RelayDefinitionCommand> first = relayDefinitionCommandService.getByImei(imei).stream()
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

    /**
     * 继电器指令返回数据处理
     *
     * @param relays
     * @param relayDefinitionCommands
     * @return
     */
    private List<String> getSendHex(List<Relay> relays, LinkedHashSet<RelayDefinitionCommand> relayDefinitionCommands) {
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
     * 传感器数据转为字符串——没有imei值的有效数据
     *
     * @param bytes 收到byte[]信息
     * @return
     */
    private String sensorDataToString(byte[] bytes) {
        //截取有效值进行分析——不要imei值
        int useLength = bytes.length - Constant.IMEI_LENGTH;
        byte[] useBytes = NettyServiceCommon.getUseBytes(bytes, useLength);
        return HexConvert.BinaryToHexString(useBytes).trim();
    }

}