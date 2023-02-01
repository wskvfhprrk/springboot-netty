//package com.hejz.nettyserver;
//
//import com.hejz.common.Constant;
//import com.hejz.entity.ChatMsg;
//import io.netty.channel.Channel;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
///**
// * @author:hejz 75412985@qq.com
// * @create: 2023-01-14 08:53
// * @Description: http请求向tcp转发消息
// */
//@Service
//@Slf4j
//public class PushMsgService {
//
//    public void pushMsgToChannel(ChatMsg chatMsg) {
//        Channel channel = Constant.USER_CHANNEL.get(chatMsg.getDtuId());
//        if (channel==null) {
//            log.error("未连接socket服务器");
//        }
//        NettyServiceCommon.write(chatMsg.getMsg(),channel);
//    }
//
//}