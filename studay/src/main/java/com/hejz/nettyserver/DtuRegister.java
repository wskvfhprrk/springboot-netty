package com.hejz.nettyserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hejz.common.Constant;
import com.hejz.entity.DtuInfo;
import com.hejz.entity.RegisterInfo;
import com.hejz.service.DtuInfoService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-14 08:41
 * @Description: dtu服务器注册功能
 */
@Component
@Slf4j
public class DtuRegister {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    DtuInfoService dtuInfoService;

    /**
     * dtu向服务器注册
     *
     * @param ctx
     * @param bytes
     */
    public void start(ChannelHandlerContext ctx, byte[] bytes) {
        synchronized (this) {
            RegisterInfo registerInfo = null;
            try {
                registerInfo = objectMapper.readValue(new String(bytes, StandardCharsets.UTF_8), RegisterInfo.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            String imei = registerInfo.getImei().trim();
            DtuInfo dtuInfo = dtuInfoService.findByImei(imei);
            register(ctx, dtuInfo);
        }

    }

    public void register(ChannelHandlerContext ctx, DtuInfo dtuInfo) {
        log.info("=========={}====dtu注册了=============",ctx.channel().id().toString());
        if (dtuInfo == null) {
            log.error("imei值:{}查询不到或没有注册，关闭通道", dtuInfo.getImei());
            ctx.channel().close();
            return;
        }
        // 之前已经绑定过了，移除并释放掉之前绑定的channel
        if (Constant.USER_CHANNEL.containsKey(dtuInfo.getId())) { // map  imei --> channel
            Channel oldChannel = Constant.USER_CHANNEL.get(dtuInfo.getId());
            oldChannel.close();
        }

        // 双向绑定
        // channel -> imei
        AttributeKey<String> key = AttributeKey.valueOf(Constant.CHANNEl_KEY);
        ctx.channel().attr(key).set(String.valueOf(dtuInfo.getId()));

        // imei -> channel
        Constant.USER_CHANNEL.put(dtuInfo.getId(), ctx.channel());
    }
}
