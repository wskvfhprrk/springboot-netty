//package com.hejz.nettyserver;
//
//import io.netty.channel.ChannelHandler;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelInboundHandlerAdapter;
//import io.netty.handler.timeout.IdleState;
//import io.netty.handler.timeout.IdleStateEvent;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
///**
// * @author:hejz 75412985@qq.com
// * @create: 2023-01-25 01:07
// * @Description: 读写空闲及向客户端发送心跳检测
// */
//@Component
//@ChannelHandler.Sharable
//@Slf4j
//public class ServerHeartBeatHandler extends ChannelInboundHandlerAdapter {
//    @Override
//    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        //判断是否是空闲状态事件
//        if (evt instanceof IdleStateEvent) {
//            IdleState state = ((IdleStateEvent) evt).state();
//            switch (state) {
//                case READER_IDLE:
//                    log.info("channel:{},空闲1分钟无上报数据自动关闭通道！",ctx.channel().id().toString());
//                    NettyServiceCommon.deleteKey(ctx.channel());
//                    ctx.channel().close();
//                    break;
//                case WRITER_IDLE:
//                    log.info("发送心跳包给客户端：00 00");
//                    //根据检查频率和实际情况写空闲时发送心跳包给客户端——60秒,如果不存活的通道就不发送了
//                    if (ctx.channel().isActive()) {
//                        NettyServiceCommon.write("0000", ctx.channel());
//                    }
//                    break;
//                case ALL_IDLE:
////                    log.info("读写都空闲");
//                    break;
//            }
//        } else { //如果不是空闲状态，向后再传播
//            ctx.fireUserEventTriggered(evt);
//        }
//    }
//}
