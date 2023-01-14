package com.hejz.nettyclient;
import com.hejz.utils.HexConvert;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;



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
        System.out.println("服务器响应的信息");
        String hex = HexConvert.BinaryToHexString(bytes);
        System.out.println(hex);
        //如果收到不是心跳信息返回给服务端
        if(!hex.equals("00 00 ")){
            ByteBuf bufff = Unpooled.buffer();
            String s = HexConvert.convertStringToHex(SystemClient.imie) + hex.replaceAll(" ","");
            System.out.println("服务器响应数据返回给服务器=="+s);
            bufff.writeBytes(HexConvert.hexStringToBytes(s));
            ctx.writeAndFlush(bufff);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}

