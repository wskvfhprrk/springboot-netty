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
 *   :拆包;
 *     :先根据imei拆分成数组进行遍历;
 *     :计算出实际地址;
 *     :根据地址位得出应该截取的长度;
 *     if(地址位==0)then(是的)
 *     :应截取长度=2;
 *     endif
 *     while (剩余长度>=应截取长度?)
 *         :截取相应长度向后传送数据;
 *         :根据地地址位截取有用的bytes信息;
 *         :直接传给下一个处理;
 *         :计算截取后剩余长度;
 *     endwhile
 *     if(剩余长度<应截取长度)then(是的)
 *     :打印错误日志;
 *     endif
 */
@Component
@ChannelHandler.Sharable
@Slf4j
public class NettyDecoder extends MessageToMessageDecoder<byte[]> {

    @Autowired
    private DtuInfoService dtuInfoService;
    @Autowired
    private CheckingRulesService checkingRulesService;

    @Override
    protected void decode(ChannelHandlerContext ctx, byte[] bytes, List list) {
        try {
            AttributeKey<Long> key = AttributeKey.valueOf(Constant.CHANNEl_KEY);
            Long dtuId = ctx.channel().attr(key).get();
            DtuInfo dtuInfo = dtuInfoService.findById(dtuId);
            //使用imei值作为分割符拆包，那后面处理就不需要考虑imei
            String[] hexArr = HexConvert.BinaryToHexString(bytes).replaceAll(" ", "").split(HexConvert.convertStringToHex(dtuInfo.getImei()));
            for (String hexStr : hexArr) {
                if (hexStr.length() != 0) {
                    //拆分指令
                    splitInstruction(list, dtuInfo, HexConvert.hexStringToBytes(hexStr));
                }
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    /**
     * 此时的指令已经没有imei值了，可能原本指令没有imei值，会出现多条指令粘包现象
     *
     * @param out
     * @param dtuInfo
     * @param bytes
     */
    private void splitInstruction(List out, DtuInfo dtuInfo, byte[] bytes) {
        //计算出实际地址
        Integer address = NettyServiceCommon.addressValueOfInstruction(dtuInfo, bytes);
        //根据地址位得出应该截取的长度
        Integer commonLength = calculatedLength(dtuInfo, address);
        //bytes值——计算长度都以bytes计算的
        //指令的byte长度
        int length = bytes.length;
        //剩余包的长度
        int remainingLength = length - commonLength;
        if (remainingLength >= 0) {
            //截取够自己的长度往后扔，把剩余的长度再递归取值
            byte[] useBytes = new byte[commonLength];
            System.arraycopy(bytes, 0, useBytes, 0, commonLength);
            out.add(useBytes);
            //剩余的bytes再扔进方法进行递归操作
            byte[] remainingBytes = new byte[remainingLength];
            if (remainingBytes.length > 0) {
                System.arraycopy(bytes, commonLength, remainingBytes, 0, remainingLength);
                splitInstruction(out, dtuInfo, remainingBytes);
            }
        }else {
            //todo 不够再粘包了——以后再开发此指令
            log.info("长度不够，不要此值：{}", bytes);
        }
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
        //if(地址位==0)then(是的)应截取长度=2;
        if (address == 0) return 2;
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