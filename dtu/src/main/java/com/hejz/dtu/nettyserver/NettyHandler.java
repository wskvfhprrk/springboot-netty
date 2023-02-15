package com.hejz.dtu.nettyserver;

import com.hejz.dtu.common.Constant;
import com.hejz.dtu.entity.DtuInfo;
import com.hejz.dtu.service.DtuInfoService;
import com.hejz.dtu.utils.HexConvert;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-24 22:51
 * @Description: 注册拦截器——每个设备都需要注册
 */
@Component
@ChannelHandler.Sharable
@Slf4j
public class NettyHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Autowired
    private DtuRegister dtuRegister;
    @Autowired
    private DtuInfoService dtuInfoService;
    @Autowired
    private NettyDecoder nettyDecoder;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        ByteBuf in = msg;
        try {
            AttributeKey<Long> key = AttributeKey.valueOf(Constant.CHANNEl_KEY);
            Long dtuId = ctx.channel().attr(key).get();
            if (dtuId != null) {
                //注册后把bytes全部向后传
                byte[] bytes = new byte[in.readableBytes()];
                in.readBytes(bytes);
                nettyDecoder.start(ctx,bytes);
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
                if(dtuInfo==null){
                    log.error("此imei={}未注册,请先注册",imei);
                    ctx.channel().close();
                    return;
                }
                dtuRegister.register(ctx, dtuInfo);
                //把所有数据后传，交给编码处理
                nettyDecoder.start(ctx,bytes);
            } else {
                byte[] bytes = new byte[in.readableBytes()];
                in.readBytes(bytes);
                log.error("通道：{},获取的byte[]长度： {} ，不能解析数据,server received message：{}", ctx.channel().id(), bytes.length, HexConvert.BinaryToHexString(bytes));
            }
        }catch (Exception e){
            log.error(e.toString());
        }
    }
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //判断是否是空闲状态事件
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            switch (state) {
                case READER_IDLE:
                    log.info("channel:{},空闲3分钟无上报数据自动关闭通道！",ctx.channel().id().toString());
                    NettyServiceCommon.deleteKey(ctx.channel());
                    ctx.channel().close();
                    break;
                case WRITER_IDLE:
                    log.info("发送心跳包给客户端：00 00");
                    //根据检查频率和实际情况写空闲时发送心跳包给客户端——60秒,如果不存活的通道就不发送了
                    if (ctx.channel().isActive()) {
                        NettyServiceCommon.write("0000", ctx.channel());
                    }
                    break;
                case ALL_IDLE:
//                    log.info("读写都空闲");
                    break;
            }
        } else { //如果不是空闲状态，向后再传播
            ctx.fireUserEventTriggered(evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.getCause();
        NettyServiceCommon.deleteKey(ctx.channel());
        ctx.channel().close();
    }
}
