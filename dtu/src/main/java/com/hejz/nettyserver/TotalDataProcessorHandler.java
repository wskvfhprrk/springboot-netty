package com.hejz.nettyserver;

import com.hejz.common.Constant;
import com.hejz.entity.DtuInfo;
import com.hejz.service.DtuInfoService;
import com.hejz.utils.HexConvert;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author:hejz 75412985@qq.com
 * * @create: 2023-01-29 08:23
 * * @Description: 总数据处理器——根据上一次解码器得到的数据结果把数据分发给感应器和继电器
 */
@Component
@ChannelHandler.Sharable
@Slf4j
public class TotalDataProcessorHandler extends SimpleChannelInboundHandler {
    @Autowired
    ProcessSensorReturnValue processSensorReturnValue;
    @Autowired
    ProcessRelayCommands processRelayCommands;
    @Autowired
    private DtuInfoService dtuInfoService;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.getCause();
        NettyServiceCommon.deleteKey(ctx.channel());
        ctx.channel().close();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) {
        try {
            start(ctx, (byte[]) msg);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void start(ChannelHandlerContext ctx, byte[] bytes) {
        try {
            AttributeKey<Long> key = AttributeKey.valueOf(Constant.CHANNEl_KEY);
            Long dtuId = ctx.channel().attr(key).get();
            DtuInfo dtuInfo = dtuInfoService.findById(dtuId);
            byte[] b = new byte[1];
            System.arraycopy(bytes, 0, b, 0, 1);  //数组截取
            String hex = "0x" + HexConvert.BinaryToHexString(b).replaceAll(" ", "");
            //地址信息

            Integer address = Integer.parseInt(hex.substring(2), 16);//从第2个字符开始截取
            if (address == 0 && bytes.length == 2) {
                log.info("心跳信息不作处理:{}", HexConvert.BinaryToHexString(bytes));
                return;
            } else {
                for (String s : dtuInfo.getSensorCheckingRulesIds().split(",")) {
                    String[] split = s.split("-");
                    if (Integer.valueOf(split[0]).equals(address)) {
                        processSensorReturnValue.start(ctx, bytes);
                        return;
                    }
                }
                for (String s : dtuInfo.getRelayCheckingRulesIds().split(",")) {
                    String[] split = s.split("-");
                    if (Integer.valueOf(split[0]).equals(address)) {
                        processRelayCommands.start(ctx, bytes);
                        return;
                    }
                }
            }
            log.error("通道：{},获取的byte[]长度： {} ，不能解析数据,server received message：{}", ctx.channel().id(), bytes.length, HexConvert.BinaryToHexString(bytes));
        }catch (Exception e){
            log.error(e.toString());
        }
    }
}