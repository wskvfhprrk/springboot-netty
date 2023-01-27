package com.hejz.nettyclient;
import com.hejz.nettyserver.NettyServiceCommon;
import com.hejz.utils.HexConvert;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //当前数据个数
        ByteBuf byteBuf =(ByteBuf) msg;
        int readableBytes = byteBuf.readableBytes();
        byte[] bytes = new byte[readableBytes];
        byteBuf.readBytes(bytes);
        log.info("服务器响应的信息");
        String hex = HexConvert.BinaryToHexString(bytes);
        log.info(hex);
        //如果收到不是心跳信息返回给服务端
        if(!hex.equals("00 00 ")){
            ByteBuf bufff = Unpooled.buffer();
            String s = HexConvert.convertStringToHex(SystemClient.imei) + hex.replaceAll(" ","");
            log.info("服务器响应数据返回给服务器=="+s);
            bufff.writeBytes(HexConvert.hexStringToBytes(s));
            ctx.writeAndFlush(bufff);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleState state = ((IdleStateEvent) evt).state();
            switch (state){
                case READER_IDLE:
                    log.info("读取器空闲,关闭通道");
//                    ctx.close();
                    break;
                case WRITER_IDLE:
                    log.info("写入程序空闲");
                    //给服务器心跳
                    NettyServiceCommon.write("0000",ctx);
                    break;
                case ALL_IDLE:
                    log.info("全部闲置");
                    break;
            }
        }else {
            ctx.fireUserEventTriggered(evt);
        }
    }
}

