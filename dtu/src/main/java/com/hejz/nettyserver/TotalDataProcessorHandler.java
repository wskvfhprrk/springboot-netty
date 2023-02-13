package com.hejz.nettyserver;

import com.hejz.common.Constant;
import com.hejz.entity.DtuInfo;
import com.hejz.service.DtuInfoService;
import com.hejz.utils.HexConvert;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author:hejz 75412985@qq.com
 * * @create: 2023-01-29 08:23
 * * @Description: 总数据处理器——根据上一次解码器得到的数据结果把数据分发给感应器和继电器
 */
@Component
@ChannelHandler.Sharable
@Slf4j
public class TotalDataProcessorHandler extends SimpleChannelInboundHandler {
    @Autowired
    ProcessSensorReturnValue processSensorReturnValue;
    @Autowired
    ProcessRelayCommands processRelayCommands;
    @Autowired
    private DtuInfoService dtuInfoService;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.getCause();
        NettyServiceCommon.deleteKey(ctx.channel());
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

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //判断是否是空闲状态事件
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            switch (state) {
                case READER_IDLE:
                    log.info("channel:{},空闲1分钟无上报数据自动关闭通道！",ctx.channel().id().toString());
                    NettyServiceCommon.deleteKey(ctx.channel());
                    ctx.channel().close();
                    break;
                case WRITER_IDLE:
                    log.info("发送心跳包给客户端：00 00");
                    //根据检查频率和实际情况写空闲时发送心跳包给客户端——60秒,如果不存活的通道就不发送了
                    if (ctx.channel().isActive()) {
                        NettyServiceCommon.write("0000", ctx.channel());
                    }
                    break;
                case ALL_IDLE:
//                    log.info("读写都空闲");
                    break;
            }
        } else { //如果不是空闲状态，向后再传播
            ctx.fireUserEventTriggered(evt);
        }
    }

    private void start(ChannelHandlerContext ctx, byte[] bytes) {
        try {
            AttributeKey<Long> key = AttributeKey.valueOf(Constant.CHANNEl_KEY);
            Long dtuId = ctx.channel().attr(key).get();
            DtuInfo dtuInfo = dtuInfoService.findById(dtuId);
            byte[] b = new byte[1];
            System.arraycopy(bytes, 0, b, 0, 1);  //数组截取
            String hex = "0x" + HexConvert.BinaryToHexString(b).replaceAll(" ", "");
            //地址信息

            Integer address = Integer.parseInt(hex.substring(2), 16);//从第2个字符开始截取
            if (address == 0 && bytes.length == 2) {
                log.info("心跳信息不作处理:{}", HexConvert.BinaryToHexString(bytes));
                return;
            } else {
                for (String s : dtuInfo.getSensorCheckingRulesIds().split(",")) {
                    String[] split = s.split("-");
                    if (Integer.valueOf(split[0]).equals(address)) {
                        processSensorReturnValue.start(ctx, bytes);
                        return;
                    }
                }
                for (String s : dtuInfo.getRelayCheckingRulesIds().split(",")) {
                    String[] split = s.split("-");
                    if (Integer.valueOf(split[0]).equals(address)) {
                        processRelayCommands.start(ctx, bytes);
                        return;
                    }
                }
            }
            log.error("通道：{},获取的byte[]长度： {} ，不能解析数据,server received message：{}", ctx.channel().id(), bytes.length, HexConvert.BinaryToHexString(bytes));
        }catch (Exception e){
            log.error(e.toString());
        }
    }
}