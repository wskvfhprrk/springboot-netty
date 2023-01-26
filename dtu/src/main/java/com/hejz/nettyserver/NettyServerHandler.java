package com.hejz.nettyserver;

import com.hejz.common.Constant;
import com.hejz.entity.DtuInfo;
import com.hejz.service.DtuInfoService;
import com.hejz.utils.HexConvert;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@ChannelHandler.Sharable
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler {
    @Autowired
    DtuRegister dtuRegister;
    @Autowired
    ProcessSensorReturnValue processSensorReturnValue;
    @Autowired
    ProcessRelayCommands processRelayCommands;
    @Autowired
    private DtuInfoService dtuInfoService;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.getCause();
        NettyServiceCommon.deleteKey(ctx.channel().id().toString());
        ctx.channel().close();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) {
        try {
            start(ctx, (byte[]) msg);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void start(ChannelHandlerContext ctx, byte[] bytes) throws Exception {
        AttributeKey<Long> key=AttributeKey.valueOf(Constant.CHANNEl_KEY);
        Long dtuId = ctx.channel().attr(key).get();
        DtuInfo dtuInfo = dtuInfoService.findById(dtuId);
        byte[] b = new byte[1];
        System.arraycopy(bytes, 0, b, 0, 1);  //数组截取
        String hex = "0x" + HexConvert.BinaryToHexString(b).replaceAll(" ", "");
        Integer adds = Integer.parseInt(hex.substring(2), 16);//从第2个字符开始截取
        for (String s : dtuInfo.getSensorCheckingRulesIds().split(",")) {
            String[] split = s.split("-");
            if(Integer.valueOf(split[0]).equals(adds)){
                processSensorReturnValue.start(ctx, bytes);
                return;
            }
        }
        for (String s : dtuInfo.getRelayCheckingRulesIds().split(",")) {
            String[] split = s.split("-");
            if(Integer.valueOf(split[0]).equals(adds)){
                processRelayCommands.start(ctx, bytes);
                return;
            }
        }
//        switch (bytes.length){
//            case Constant.DTU_POLLING_RETURN_LENGTH: //处理dtu轮询返回值
//                new Thread(() -> {
//                    processSensorReturnValue.start(ctx, bytes);
//                }).start();
//                break;
//            case Constant.RELAY_RETURN_VALUES_LENGTH: //处理继电器返回值
//                new Thread(() -> {
//                    processRelayCommands.start(ctx, bytes);
//                }).start();
//                break;
//            default:
                log.error("通道：{},获取的byte[]长度： {} ，不能解析数据,server received message：{}", ctx.channel().id(), bytes.length, HexConvert.BinaryToHexString(bytes));
//        }
    }
}