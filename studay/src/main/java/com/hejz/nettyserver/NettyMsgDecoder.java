package com.hejz.nettyserver;

import com.hejz.common.Constant;
import com.hejz.entity.CheckingRules;
import com.hejz.entity.DtuInfo;
import com.hejz.service.CheckingRulesService;
import com.hejz.service.DtuInfoService;
import com.hejz.utils.HexConvert;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-24 21:37
 * @Description: 消息解码器——为了拆包解包,不要imei值
 */
@Component
@ChannelHandler.Sharable
@Slf4j
public class NettyMsgDecoder extends MessageToMessageDecoder<byte[]> {

    @Autowired
    private DtuInfoService dtuInfoService;
    @Autowired
    private CheckingRulesService checkingRulesService;

    @Override
    protected void decode(ChannelHandlerContext ctx, byte[] bytes, List list) throws Exception {
        AttributeKey<Long> key = AttributeKey.valueOf(Constant.CHANNEl_KEY);
        Long dtuId = ctx.channel().attr(key).get();
        DtuInfo dtuInfo = dtuInfoService.findById(dtuId);
        //imei在前取出imei长度+1位就是地址位令仅为一位
        //有用数据建立在dtu中地址
        Integer useLength = 0;
        byte[] b = new byte[1];
        System.arraycopy(bytes, Constant.IMEI_LENGTH, b, 0, 1);  //数组截取
        String hex = "0x" + HexConvert.BinaryToHexString(b).replaceAll(" ", "");
        Integer adds = Integer.parseInt(hex.substring(2), 16);//从第2个字符开始截取
        String relayCheckingRulesIds = dtuInfo.getRelayCheckingRulesIds() + "," + dtuInfo.getSensorCheckingRulesIds();
        for (String s : relayCheckingRulesIds.split(",")) {
            String[] split = s.split("-");
            if (Integer.valueOf(split[0]).equals(adds)) {
                CheckingRules checkingRules = checkingRulesService.findById(Integer.valueOf(split[1]));
                useLength = checkingRules.getCommonLength();
                break;
            }
        }
        //如果大于等于加上imei值的话就可以拆包，去除imei值了
        if (bytes.length - Constant.IMEI_LENGTH - useLength >= 0) {
            //只要有用的才可以用
            byte[] useBytes = NettyServiceCommon.getUseBytes(bytes, useLength);
            list.add(useBytes);
        }else {
            //不够就等待
            log.info("长度不够，继续等待");
            return;
        }
    }

}