package com.hejz.nettyserver;

import com.hejz.common.Constant;
import com.hejz.utils.HexConvert;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
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


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //判断是否是空闲状态事件
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            switch (state) {
                case READER_IDLE:
                    log.info("channel:{},空闲3分钟无上报数据自动关闭通道！",ctx.channel().id().toString());
                    ctx.channel().close();
                    break;
                case WRITER_IDLE:
                    log.info("发送心跳包给客户端：00 00");
                    //根据检查频率和实际情况写空闲时发送心跳包给客户端——60秒,如果不存活的通道就不发送了
                    if (ctx.channel().isActive()) {
                        NettyServiceCommon.write("0000", ctx);
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

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.getCause();
        ctx.channel().close();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) {
        try {
            start(ctx, (ByteBuf) msg);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void start(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        //当前数据个数
        ByteBuf byteBuf = msg;
        //获取缓冲区可读字节数
        int readableBytes = byteBuf.readableBytes();
        byte[] bytes = new byte[readableBytes];
        byteBuf.readBytes(bytes);
        //dtu必须开通注册功能，开通注册才可以查询到信息
        switch (readableBytes){
            case Constant.DUT_REGISTERED_BYTES_LENGTH:
                dtuRegister.start(ctx,bytes);
                break;
            case Constant.DTU_POLLING_RETURN_LENGTH: //处理dtu轮询返回值
                new Thread(() -> {
                    processSensorReturnValue.start(ctx, bytes);
                }).start();
                break;
            case Constant.RELAY_RETURN_VALUES_LENGTH: //处理继电器返回值
                new Thread(() -> {
                    processRelayCommands.start(ctx, bytes);
                }).start();
                break;
            default:
                log.error("通道：{},获取的byte[]长度： {} ，不能解析数据,server received message：{}", ctx.channel().id(), readableBytes, HexConvert.BinaryToHexString(bytes));
        }
    }
}