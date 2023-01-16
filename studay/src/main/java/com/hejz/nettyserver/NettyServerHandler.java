package com.hejz.nettyserver;

import com.hejz.common.Constant;
import com.hejz.utils.HexConvert;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

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
        int time = Constant.INTERVAL_TIME_MAP.get(ctx.channel().id().toString()) == null ? Constant.INTERVAL_TIME : Constant.INTERVAL_TIME_MAP.get(ctx.channel().id().toString());
         //EventLoop 实现定时任务
        ctx.channel().eventLoop().scheduleWithFixedDelay(new Runnable() {
            // 因为线程中不能访问外部局部变量
            // 这里所以采用在线程中创建属性、属性的赋值方法，然后在创建线程时，通过调用这个自身的方法，实现局部变量的方位。
            ChannelHandlerContext ctx;
            @Override
            public void run() {
                if (ctx.channel().isActive()) {
                    //发送空包（定义一个实体）
                    NettyServiceCommon.write("0000", ctx);
                }
            }
            //对自身属性进行赋值
            public Runnable accept(ChannelHandlerContext chct) {
                this.ctx = chct;
                return this;
            }
        }.accept(ctx), 0, time, TimeUnit.SECONDS);
        ctx.executor().schedule(() -> {
            if (ctx.channel().isActive()) {
                //发送空包（定义一个实体）
                NettyServiceCommon.write("0000",ctx);
            }
        }, time, TimeUnit.SECONDS);
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
                try {
                    start(ctx, (ByteBuf) msg);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        });

    }

    private void start(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        //当前数据个数
        ByteBuf byteBuf = msg;
        //获取缓冲区可读字节数
        int readableBytes = byteBuf.readableBytes();
        byte[] bytes = new byte[readableBytes];
        byteBuf.readBytes(bytes);
        //dtu必须开通注册功能，开通注册才可以查询到信息
        if (readableBytes == Constant.DUT_REGISTERED_BYTES_LENGTH) {
            dtuRegister.start(ctx);
        } else if (readableBytes == (Constant.DTU_POLLING_RETURN_LENGTH)) { //处理dtu轮询返回值
            new Thread(() -> {
                processSensorReturnValue.start(ctx, bytes);
            }).start();
        } else if (readableBytes == (Constant.RELAY_RETURN_VALUES_LENGTH)) { //处理继电器返回值
            new Thread(() -> {
                processRelayCommands.start(ctx, bytes);
            }).start();
        } else {
            log.error("通道：{},获取的byte[]长度： {} ，不能解析数据,server received message：{}", ctx.channel().id(), readableBytes, HexConvert.BinaryToHexString(bytes));
        }
    }

}