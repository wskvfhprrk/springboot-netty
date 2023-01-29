package com.hejz.nettyclient;

import com.hejz.common.Constant;
import com.hejz.utils.CRC16;
import com.hejz.utils.HexConvert;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SystemClient {

    private String host;
    private int port;
    public static String imei;

    public SystemClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public void run() throws InterruptedException {
        //配置客户端的线程组，客户端只有一个线程组，服务端是EventLoopGroup bossGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //放入自己的业务Handler
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new IdleStateHandler(120, 120, 120, TimeUnit.SECONDS));
                            pipeline.addLast(new ClientHandler());
                        }
                    });
            //发起异步连接操作，同步阻等待结果
            ChannelFuture future = bootstrap.connect(host, port).sync();
            try {
                start(future);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //等待客户端链路关闭
            future.channel().closeFuture().sync();
        } finally {
            //释放NIO线程组
            group.shutdownGracefully();
        }
    }


    private void start(ChannelFuture future) throws IOException {
        Long im=865328063321359L;
        for (int i = 0; i < 10; i++) {
            System.out.println("选择"+i+"===="+im+i);
        }
        InputStreamReader is = new InputStreamReader(System.in, "UTF-8");
        BufferedReader br = new BufferedReader(is);
        System.out.println("请选择你的imei>>>");
        //向服务器发送数据
        int data = Integer.parseInt(br.readLine());
        String imei = null;
        switch (data){
            case 0:
                imei="865328063321359";
                break;
            case 1:
                imei="865328063321360";
                break;
            case 2:
                imei="865328063321361";
                break;
            case 3:
                imei="865328063321362";
                break;
            case 4:
                imei="865328063321363";
                break;
            case 5:
                imei="865328063321364";
                break;
            case 6:
                imei="865328063321365";
                break;
            case 7:
                imei="865328063321366";
                break;
            case 8:
                imei="865328063321367";
                break;
            case 9:
                imei="865328063321368";
                break;
            default:
                System.out.println("只能选择0-9");
        }
        System.out.println("您选择的imei值===>"+imei);
        SystemClient.imei = imei;
        //发送的正常指令测试
//        NormalCommandTest.instructionsSent(future, imei);
        //编码粘包拆包测试
//        CodeStickingAndUnpackingTest.instructionsSent(future, imei);
        ///编码粘包拆包含有心跳信息测试
        CodeStickingAndUnpackingContainsHeartbeatInformationTest.instructionsSent(future, imei);
        br.close();
        is.close();
    }



    public static void main(String[] args) {
//        SystemClient client = new SystemClient("192.168.0.106", 9090);
        SystemClient client = new SystemClient("127.0.0.1", 9090);
        try {
            client.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
