//package com.hejz.nettyclient;
//
//import com.hejz.common.Constant;
//import com.hejz.entity.RegisterInfo;
//import com.hejz.utils.CRC16;
//import com.hejz.utils.HexConvert;
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.ChannelFuture;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Locale;
//
///**
// * @author:hejz 75412985@qq.com
// * @create: 2023-01-29 08:54
// * @Description: 编码粘包拆包含有心跳信息测试——imei注册后数据不带imei
// */
//@Slf4j
//public class CodeStickingAndUnpackingContainsHeartbeatInformationTest1 {
//    public static void instructionsSent(ChannelFuture future, String imei) {
//        log.info("编码粘包拆包含有心跳信息测试——imei注册后数据不带imei>>>>>>>>>>>");
//        //注册
//        RegisterInfo registerInfo=new RegisterInfo();
//        registerInfo.setFver("YED_DTU_1.2.3");
//        registerInfo.setIccid("89861122222045681451");
//        registerInfo.setImei(imei);
//        registerInfo.setCsq("22");
//        ByteBuf buff1 = Unpooled.buffer();
//        buff1.writeBytes(HexConvert.hexStringToBytes(registerInfo.toString()));
//        future.channel().writeAndFlush(buff1);
//        List<String> instructionsSent = new ArrayList<>();
//        List<Integer> data = Arrays.asList(100, 610, 1200, 70, 300, 30, 30, 170, 20);
//        for (int i = 0; i < data.size(); i++) {
//            instructionsSent.add(calculateRrc16ValidatedData(i<2?"010302":"020302", data.get(i)));
//            //加入心跳数据
//            if(i==5){
//                instructionsSent.add("0001");
//                instructionsSent.add("03050000FF008DD8");
//            }
//        }
//        for (int i = 0; i < 100; i++) {
//            StringBuffer sb=new StringBuffer();
//            for (String hex : instructionsSent) {
//                sb.append(hex);
//            }
//            log.info("发送给服务器的==" + i + "==内容======" + sb.toString());
//            //netty需要用ByteBuf传输
//            ByteBuf bufff = Unpooled.buffer();
//            bufff.writeBytes(HexConvert.hexStringToBytes(sb.toString()));
//            future.channel().writeAndFlush(bufff);
//            try {
//                int time = Constant.INTERVAL_TIME_MAP.get(future.channel().id().toString()) == null ? Constant.INTERVAL_TIME : Constant.INTERVAL_TIME_MAP.get(future.channel().id().toString());
//                log.info("time=={}", time);
//                Thread.sleep(time);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 计算RRC16验证过的数据
//     *
//     * @param previousData 前面部分的数据：地址+功能码+数据位
//     * @param i            数据位——10进制数据
//     * @return
//     */
//    private static String calculateRrc16ValidatedData(String previousData, Integer i) {
//        String hexString = Integer.toHexString(i).toUpperCase(Locale.ROOT);
//        if (hexString.length() == 1) {
//            hexString = "000" + hexString;
//        } else if (hexString.length() == 2) {
//            hexString = "00" + hexString;
//        } else if (hexString.length() == 3) {
//            hexString = "0" + hexString;
//        }
//        byte[] bytes = HexConvert.hexStringToBytes(previousData + hexString);
//        String validatedData = CRC16.getCRC3(bytes);
//        String result = previousData + hexString + validatedData;
//        return result;
//    }
//}
