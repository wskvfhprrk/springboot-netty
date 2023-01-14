package com.hejz.nettyserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hejz.common.Constant;
import com.hejz.entity.RegisterInfo;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

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
    /**
     * dtu向服务器注册
     *
     * @param bytes
     * @param ctx
     */
    public void start(byte[] bytes, ChannelHandlerContext ctx) {
        log.info("==============dtu注册了=============");
        RegisterInfo registerInfo = null;
        try {
            registerInfo = objectMapper.readValue(new String(bytes, StandardCharsets.UTF_8), RegisterInfo.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String imei = registerInfo.getImei().trim();
//        log.info("channel.id()={} imei======={}", ctx.channel().id().toString(), imei);
        //注册信息后把时间加上30秒——目的是为了第一次获取有效的数据
        Constant.END_TIME_MAP.put(ctx.channel().id().toString(), LocalDateTime.now().minusSeconds(Constant.INTERVAL_TIME + 30));
    }
}
