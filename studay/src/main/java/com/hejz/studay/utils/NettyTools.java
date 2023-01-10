package com.hejz.studay.utils;//package utils;
//
//import java.util.Objects;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.TimeUnit;
//
//import com.google.common.cache.Cache;
//import com.google.common.cache.CacheBuilder;
//
//public class NettyTools {
//    /**
//     * 响应消息缓存
//     */
//    private static Cache<String, BlockingQueue<String>> responseMsgCache = CacheBuilder.newBuilder()
//            .maximumSize(50000)
//            .expireAfterWrite(100, TimeUnit.SECONDS)
//            .build();
//    /**
//     * 等待响应消息
//     * @param key 消息唯一标识
//     * @return ReceiveDdcMsgVo
//     */
//    public static String waitReceiveMsg(String key) {
//
//        try {
//            //设置超时时间
//            String vo = Objects.requireNonNull(responseMsgCache.getIfPresent(key))
//                .poll(3000, TimeUnit.MILLISECONDS);
//
//            //删除key
//            responseMsgCache.invalidate(key);
//            return vo;
//        } catch (Exception e) {
//
//            System.out.println("获取数据异常,sn="+key+",msg=null");
//
//            return null;
//        }
//
//    }
//
//    /**
//     * 初始化响应消息的队列
//     * @param key 消息唯一标识
//     */
//    public static void initReceiveMsg(String key) {
//        responseMsgCache.put(key,new LinkedBlockingQueue<String>(1));
//    }
//
//    /**
//     * 设置响应消息
//     * @param key 消息唯一标识
//     */
//    public static void setReceiveMsg(String key, String msg) {
//
//        if(responseMsgCache.getIfPresent(key) != null){
//            responseMsgCache.getIfPresent(key).add(msg);
//            return;
//        }
//
//        System.out.println("sn "+key+"不存在");
//    }
//
//}
