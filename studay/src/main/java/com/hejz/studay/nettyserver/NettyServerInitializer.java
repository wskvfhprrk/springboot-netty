package com.hejz.studay.nettyserver;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    Logger log = LogManager.getLogger(NettyServerInitializer.class);
    @Autowired
    private NettyServerHandler nettyServerHandler;
    //连接注册，创建成功，会被调用
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        log.info("==================netty报告==================");
        log.info("信息：有一客户端链接到本服务端");
        log.info("IP:{}" , ch.localAddress().getHostName());
        log.info("Port:{}" , ch.localAddress().getPort());
        log.info("==================netty报告完毕==================");
        ChannelPipeline pipeline = ch.pipeline();
        // 自定义解码器
//        pipeline.addLast(new NettyMessageDecoder());

        // 自定义编码器
//        pipeline.addLast(new NettyMessageEncoder());
        pipeline.addLast(nettyServerHandler);
    }

}