package com.hejz.nettyclient;

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
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class SystemClient {

    private String host;
    private int port;

    public SystemClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public void run() throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new ObjectEncoder())
                                    .addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)))
                                    .addLast(new ClientHandler());
                        }
                    });

            ChannelFuture future = bootstrap.connect(host, port).sync();
            try {
                start(future);
            } catch (IOException e) {
                e.printStackTrace();
            }
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }


    private void start(ChannelFuture future) throws IOException {
        InputStreamReader is = new InputStreamReader(System.in, "UTF-8");
        BufferedReader br = new BufferedReader(is);
        System.out.println("请输入你的imei>>>");
        // TODO: 2023/1/13 向服务器发送数据
        String imei = br.readLine();
        //发送的指令
        for (int i = 0; i < 100; i++) {
            List<String> instructions_sent = Arrays.asList("030500000000CC28", "0305000100009DE8", "0305000200006DE8", "0305000300003C28");
            for (String s : instructions_sent) {
                String hex = HexConvert.convertStringToHex(imei) + s;
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("发送给服务器的内容===" + hex);
                //netty需要用ByteBuf传输
                ByteBuf bufff = Unpooled.buffer();
                ByteBuf byteBuf = bufff.writeBytes(HexConvert.hexStringToBytes(hex));
                future.channel().writeAndFlush(bufff);
            }
            br.close();
            is.close();
        }
    }

    public static void main(String[] args) {
        SystemClient client = new SystemClient("127.0.0.1", 9090);
        try {
            client.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
