package com.hejz.dtu.nettyserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hejz.dtu.common.Constant;
import com.hejz.dtu.entity.DtuInfo;
import com.hejz.dtu.entity.RegisterInfo;
import com.hejz.dtu.service.DtuInfoService;
import com.hejz.dtu.utils.HexConvert;
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
            RegisterInfo registerInfo;
            String imei;
            try {
                registerInfo = objectMapper.readValue(new String(bytes, StandardCharsets.UTF_8), RegisterInfo.class);
                 imei = registerInfo.getImei().trim();
            } catch (JsonProcessingException e) {
                log.info("RegisterInfo序列化不了就是以imei开头消息，以imei为开头消息进行注册");
                byte[] imeiBytes=new byte[Constant.IMEI_LENGTH];
                System.arraycopy(bytes,0,imeiBytes,0,Constant.IMEI_LENGTH);
                imei= HexConvert.BinaryToHexString(imeiBytes).replaceAll(" ","").trim();
            }

            DtuInfo dtuInfo = dtuInfoService.findByImei(imei);
            if (dtuInfo == null) {
                log.error("imei值:{}查询不到或没有注册，关闭通道", imei);
                NettyServiceCommon.deleteKey(ctx.channel());
                ctx.channel().close();
                return;
            }
            register(ctx, dtuInfo);
        }

    }

    public void register(ChannelHandlerContext ctx, DtuInfo dtuInfo) {
        log.info("=========={}====dtu注册了=============",ctx.channel().id().toString());

        // 之前已经绑定过了，移除并释放掉之前绑定的channel
        if (Constant.USER_CHANNEL.containsKey(dtuInfo.getId())) { // map  imei --> channel
            Channel oldChannel = Constant.USER_CHANNEL.get(dtuInfo.getId());
            log.info("imei:{} 冲突,原通道：{} 被关闭",dtuInfo.getImei(),oldChannel.id().toString());
            NettyServiceCommon.deleteKey(oldChannel);
            oldChannel.close();
        }

        // 双向绑定
        // channel -> dtuId
        AttributeKey<Long> key = AttributeKey.valueOf(Constant.CHANNEl_KEY);
        ctx.channel().attr(key).set(dtuInfo.getId());

        // dtuId -> channel
        Constant.USER_CHANNEL.put(dtuInfo.getId(), ctx.channel());
        Constant.CHANNELGROUP.add(ctx.channel());

        log.info("=========={}====dtu注册完成=============",ctx.channel().id().toString());
    }
}
