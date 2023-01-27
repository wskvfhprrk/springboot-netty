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
import io.netty.handler.codec.serialization.ObjectEncoder;
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
        //发送的指令
        instructionsSent(future, imei);
        br.close();
        is.close();
    }

    private void instructionsSent(ChannelFuture future, String imei) {
        List<String> instructionsSent = new ArrayList<>();
        //空气温度,空气湿度,土壤PH,土壤温度,土壤湿度,土壤氮,土壤磷,土壤钾,土壤电导率
        //一般缩小10倍是真实数据
//        List<Integer> data = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            data.add(100 + i * 10);
//        }
//        data.add(610);
//        data.add(1200);
//        data.add(70);
//        data.add(300);
//        data.add(30);
//        data.add(30);
//        data.add(170);
//        data.add(20);

        List<Integer> data = Arrays.asList(100, 610, 1200, 70, 300, 30, 30, 170, 20);
        for (int i = 0; i < data.size(); i++) {
            instructionsSent.add(calculateRrc16ValidatedData("020302", data.get(i)));
        }
        for (int i = 0; i < 100; i++) {
//            log.info();("=========================发送一组新数据===========================");
            for (String s : instructionsSent) {
                String hex = HexConvert.convertStringToHex(imei) + s;
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("发送给服务器的==" + i + "==内容======" + hex);
                //netty需要用ByteBuf传输
                ByteBuf bufff = Unpooled.buffer();
                bufff.writeBytes(HexConvert.hexStringToBytes(hex));
                future.channel().writeAndFlush(bufff);
            }
            try {
                int time = Constant.INTERVAL_TIME_MAP.get(future.channel().id().toString()) == null ? Constant.INTERVAL_TIME : Constant.INTERVAL_TIME_MAP.get(future.channel().id().toString());
                log.info("time=={}", time);
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 计算RRC16验证过的数据
     *
     * @param previousData 前面部分的数据：地址+功能码+数据位
     * @param i            数据位——10进制数据
     * @return
     */
    private String calculateRrc16ValidatedData(String previousData, Integer i) {
        String hexString = Integer.toHexString(i).toUpperCase(Locale.ROOT);
        if (hexString.length() == 1) {
            hexString = "000" + hexString;
        } else if (hexString.length() == 2) {
            hexString = "00" + hexString;
        } else if (hexString.length() == 3) {
            hexString = "0" + hexString;
        }
        byte[] bytes = HexConvert.hexStringToBytes(previousData + hexString);
        String validatedData = CRC16.getCRC3(bytes);
        String result = previousData + hexString + validatedData;
        return result;
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
