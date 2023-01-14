package com.hejz.nettyserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hejz.common.Constant;
import com.hejz.entity.RegisterInfo;
import com.hejz.repository.CommandStatusRepository;
import com.hejz.service.DtuInfoService;
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
    @Autowired
    private DtuInfoService dtuInfoService;

    /**
     * dtu向服务器注册
     *
     * @param ctx
     */
    public void start(ChannelHandlerContext ctx) {
        log.info("=========={}====dtu注册了=============",ctx.channel().id().toString());
//        try {
//            objectMapper.readValue(new String(bytes, StandardCharsets.UTF_8), RegisterInfo.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
    }
}
