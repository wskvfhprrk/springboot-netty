package com.hejz.dtu.nettyserver;

import com.hejz.dtu.common.Constant;
import com.hejz.dtu.entity.DtuInfo;
import com.hejz.dtu.service.DtuInfoService;
import com.hejz.dtu.utils.HexConvert;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * * @create: 2023-01-29 08:23
 * * @Description: 总数据处理器——根据上一次解码器得到的数据结果把数据分发给感应器和继电器
 */
@Component
@Slf4j
public class TotalDataProcessorHandler {
    @Autowired
    ProcessSensorReturnValue processSensorReturnValue;
    @Autowired
    ProcessRelayCommands processRelayCommands;
    @Autowired
    private DtuInfoService dtuInfoService;

    public void start(ChannelHandlerContext ctx, byte[] bytes) {
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
                String sensorAddressOrder = dtuInfo.getSensorAddressOrder();
                //如果接收到数据地址在感应器指令中说明是感应器的数据
                List<String> list = Arrays.asList(sensorAddressOrder.split(","));
                if(list.contains(address.toString())){
                    processSensorReturnValue.start(ctx, bytes);
                }else {
                    processRelayCommands.start(ctx, bytes);
                }
            }
        }catch (Exception e){
            log.error("错误hex数据：{}",HexConvert.BinaryToHexString(bytes));
            log.error("错误字符数据：{}",new String(bytes, StandardCharsets.UTF_8));
            log.error(e.toString());
        }
    }
}