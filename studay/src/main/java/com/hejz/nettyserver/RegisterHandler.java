package com.hejz.nettyserver;

import com.hejz.common.Constant;
import com.hejz.entity.DtuInfo;
import com.hejz.utils.HexConvert;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-24 22:51
 * @Description: 注册拦截器——每个设备都需要注册
 */
@Component
@ChannelHandler.Sharable
@Slf4j
public class RegisterHandler extends MessageToMessageDecoder<ByteBuf> {
    @Autowired
    private DtuRegister dtuRegister;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        AttributeKey<Long> key = AttributeKey.valueOf(Constant.CHANNEl_KEY);
        Long dtuId = ctx.channel().attr(key).get();
        if (dtuId != null) {
            //注册后把bytes全部向后传
            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            list.add(bytes);
        } else if (byteBuf.readableBytes() == Constant.DUT_REGISTERED_BYTES_LENGTH) {
            //去注册
            byte[] bytes = new byte[Constant.DUT_REGISTERED_BYTES_LENGTH];
            byteBuf.readBytes(bytes);
            log.info("进入注册拦截器……………………进行注册");
            dtuRegister.start(ctx, bytes);
        } else if (byteBuf.readableBytes() >= Constant.IMEI_LENGTH) {
            //先获取imei注册再把其它的数据交给后面处理
            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            DtuInfo dtuInfo = NettyServiceCommon.calculationDtuInfo(bytes);
            dtuRegister.register(ctx,dtuInfo);
            //把所有数据后传，交给编码处理
            list.add(bytes);
        }
    }
}
