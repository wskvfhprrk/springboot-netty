package com.hejz.nettyserver;

import com.hejz.common.Constant;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

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
        log.info("IP:{}", ch.remoteAddress().getAddress());
        log.info("Port:{}", ch.remoteAddress().getPort());
        log.info("通道id:{}", ch.id().toString());
        log.info("==================netty报告完毕==================");
        ChannelPipeline pipeline = ch.pipeline();
        //一个周期3倍时间之内读事件 则断开连接
        int time = Constant.INTERVAL_TIME_MAP.get(ch.id().toString()) == null ? Constant.INTERVAL_TIME : Constant.INTERVAL_TIME_MAP.get(ch.id().toString());
        pipeline.addLast(new ReadTimeoutHandler(time * 3, TimeUnit.SECONDS));
        // 自定义解码器
//        pipeline.addLast(new NettyMessageDecoder());

        // 自定义编码器
//        pipeline.addLast(new NettyMessageEncoder());
        pipeline.addLast(nettyServerHandler);
    }

}