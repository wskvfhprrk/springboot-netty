package com.hejz.nettyserver;

import com.hejz.common.Constant;
import com.hejz.entity.CheckingRules;
import com.hejz.entity.DtuInfo;
import com.hejz.service.CheckingRulesService;
import com.hejz.service.DtuInfoService;
import com.hejz.utils.HexConvert;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        //使用imei值作为分割符拆包，那后面处理就不需要考虑imei
        String[] s1 = HexConvert.BinaryToHexString(bytes).replaceAll(" ", "").split(HexConvert.convertStringToHex(dtuInfo.getImei()));
        for (String hexStr : s1) {
            if (hexStr.length() != 0) {
                //拆分指令
                splitInstruction(list, dtuInfo, hexStr);
            }
        }
    }

    /**
     * 此时的指令已经没有imei值了，可能原本指令没有imei值，会出现多条指令粘包现象
     *
     * @param out
     * @param dtuInfo
     * @param hexStr
     */
    private void splitInstruction(List out, DtuInfo dtuInfo, String hexStr) {
        //指令的地址值
        Integer address = addressValueOfInstruction(dtuInfo, hexStr);
        //计算指令的长度
        Integer commonLength = calculatedLength(dtuInfo, address);
        //bytes值——计算长度都以bytes计算的
        byte[] bytes = HexConvert.hexStringToBytes(hexStr);
        //指令的byte长度
        int length = bytes.length;
        //剩余包的长度
        int remainingLength = length - commonLength;
        if (address.equals("0")) {
            //心跳不作处理
            log.info("检测到客户端imei:{}心跳：{}", dtuInfo.getImei(), hexStr);
        }
        //拆包
        if (remainingLength == 0) {
            //只要有用的才可以用
            out.add(bytes);
        } else if (remainingLength > 0) {
            //截取够自己的长度往后扔，把剩余的长度再递归取值
            byte[] useBytes = new byte[commonLength];
            System.arraycopy(bytes, 0, useBytes, 0, commonLength);
            //剩余的bytes再扔进方法进行递归操作
            byte[] remainingBytes = new byte[remainingLength];
            System.arraycopy(bytes, commonLength, remainingBytes, 0, remainingLength);
            String s = HexConvert.BinaryToHexString(remainingBytes).replace(" ", "");
            splitInstruction(out, dtuInfo, s);
            out.add(useBytes);
        } else {
            //todo 不够再粘包了——以后再开发此指令
            log.info("长度不够，不要此值：{}", hexStr);
        }
    }

    /**
     * 指令的地址值
     *
     * @param dtuInfo
     * @param hexStr  16进制指令
     * @return
     */
    private Integer addressValueOfInstruction(DtuInfo dtuInfo, String hexStr) {
        Integer address;
        if (dtuInfo.getNoImei()) {
            String hex = "0x" + hexStr.substring(0, 2);
            address = Integer.parseInt(hex.substring(2), 16);//从第2个字符开始截取
        } else {
            String hex = "0x" + hexStr.substring(0, 2);
            address = Integer.parseInt(hex.substring(2), 16);//从第2个字符开始截取
        }
        return address;
    }

    /**
     * 根据dtuInfo信息和地址计算指令长度
     *
     * @param dtuInfo
     * @param address
     * @return
     */
    private Integer calculatedLength(DtuInfo dtuInfo, Integer address) {
        Integer commonLength = 0;
        String relayCheckingRulesIds = dtuInfo.getRelayCheckingRulesIds() + "," + dtuInfo.getSensorCheckingRulesIds();
        for (String relayCheckingRulesId : relayCheckingRulesIds.split(",")) {
            String[] split = relayCheckingRulesId.split("-");
            if (Integer.valueOf(split[0]).equals(address)) {
                CheckingRules checkingRules = checkingRulesService.findById(Integer.valueOf(split[1]));
                commonLength = checkingRules.getCommonLength();
                break;
            }
        }
        return commonLength;
    }


}