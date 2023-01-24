package com.hejz.nettyserver;

import com.hejz.common.Constant;
import com.hejz.entity.DtuInfo;
import com.hejz.service.DtuInfoService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-24 21:37
 * @Description: 消息解码器——为了拆包解包,只能私有不允许共享的
 */
@Component
@ChannelHandler.Sharable
@Slf4j
public class NettyMsgDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Autowired
    private DtuInfoService dtuInfoService;
    @Autowired
    private DtuRegister dtuRegister;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List list) throws Exception {
        log.info("byteBuf.readableBytes()={}", byteBuf.readableBytes());
        //如果是注册信息——根据字节判断，直接放行，不进行编码
        if (byteBuf.readableBytes() == Constant.DUT_REGISTERED_BYTES_LENGTH) {
            //去注册
            byte[] bytes = new byte[Constant.DUT_REGISTERED_BYTES_LENGTH];
            byteBuf.readBytes(bytes);
            dtuRegister.start(ctx, bytes);
        } else {
            //要先有注册信息，如果没有就必须每条信息前加入imei信息（有注册信息可能也会有imei信息）
            AttributeKey<Long> key = AttributeKey.valueOf(Constant.CHANNEl_KEY);
            Long dtuId = ctx.channel().attr(key).get();
            if (dtuId == null) {
                //取前15位信息注册
                int imeiLength = 15;
                if (byteBuf.readableBytes() > imeiLength) {
                    byte[] imeiBytes = new byte[imeiLength];
                    DtuInfo dtuInfo = NettyServiceCommon.calculationDtuInfo(imeiBytes);
                    dtuRegister.register(ctx, dtuInfo);
                }
            } else {
                int length = 0;
                if (byteBuf.readableBytes() > 4) {
                    if (length == 0) {
                        length = byteBuf.readInt();
                        if (byteBuf.readableBytes() < length) {
                            log.info("长度不够，继续等待");
//                        return;
//                        byte[] content = new byte[length];
//                        byteBuf.readBytes(content);
//                        //发给下一个handler
//                        list.add(content);
                            length = 0;
                        }
                    }
                }
            }
        }
    }

}