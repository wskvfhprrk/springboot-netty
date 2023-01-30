package com.hejz.nettyserver;

import com.hejz.common.Constant;
import com.hejz.entity.DtuInfo;
import com.hejz.service.DtuInfoService;
import com.hejz.utils.HexConvert;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
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
    @Autowired
    private DtuInfoService dtuInfoService;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        AttributeKey<Long> key = AttributeKey.valueOf(Constant.CHANNEl_KEY);
        Long dtuId = ctx.channel().attr(key).get();
        if (dtuId != null) {
            //注册后把bytes全部向后传
            byte[] bytes = new byte[in.readableBytes()];
            in.readBytes(bytes);
            out.add(bytes);
        } else if (in.readableBytes() == Constant.DUT_REGISTERED_BYTES_LENGTH) {
            //去注册
            byte[] bytes = new byte[Constant.DUT_REGISTERED_BYTES_LENGTH];
            in.readBytes(bytes);
            log.info("进入注册拦截器……………………进行注册");
            dtuRegister.start(ctx, bytes);
        } else if (in.readableBytes() >= Constant.IMEI_LENGTH) {
            //先获取imei注册再把其它的数据交给后面处理
            byte[] bytes = new byte[in.readableBytes()];
            in.readBytes(bytes);
            byte[] imeiBytes = new byte[Constant.IMEI_LENGTH];
            System.arraycopy(bytes, 0, imeiBytes, 0, Constant.IMEI_LENGTH);
            String imei = HexConvert.hexStringToString(HexConvert.BinaryToHexString(imeiBytes).replaceAll(" ", ""));
            DtuInfo dtuInfo = dtuInfoService.findByImei(imei.trim());
            dtuRegister.register(ctx,dtuInfo);
            //把所有数据后传，交给编码处理
            out.add(bytes);
        }else {
            byte[] bytes = new byte[in.readableBytes()];
            in.readBytes(bytes);
            log.error("通道：{},获取的byte[]长度： {} ，不能解析数据,server received message：{}", ctx.channel().id(), bytes.length, HexConvert.BinaryToHexString(bytes));
        }
    }
}
