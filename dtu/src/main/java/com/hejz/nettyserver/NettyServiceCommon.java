package com.hejz.nettyserver;

import com.hejz.common.Constant;
import com.hejz.entity.CheckingRules;
import com.hejz.service.CheckingRulesService;
import com.hejz.service.DtuInfoService;
import com.hejz.utils.CRC16;
import com.hejz.utils.HexConvert;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.Duration;
import java.util.List;
import java.util.Set;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-14 08:53
 * @Description: 工具类
 */
@Component
@Slf4j
public class NettyServiceCommon {

    @Autowired
    private DtuInfoService dtuInfoService1;
    private static DtuInfoService dtuInfoService;
    @Autowired
    private CheckingRulesService checkingRulesService1;
    private static CheckingRulesService checkingRulesService;
    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate1;
    private static RedisTemplate redisTemplate;

    /**
     * 删除所有缓存和内存中map
     *
     * @param channelId
     */
    public static void deleteKey(String channelId) {

        Constant.INTERVAL_TIME_MAP.remove(channelId);
        Constant.SENSOR_DATA_BYTE_LIST_MAP.remove(channelId);
        Constant.THREE_RECORDS_MAP.remove(channelId + "min");
        Constant.THREE_RECORDS_MAP.remove(channelId + "max");

        Set<String> keys = redisTemplate.keys(Constant.CACHE_INSTRUCTIONS_THAT_NEED_TO_CONTINUE_PROCESSING_CACHE_KEY + "::" + channelId + ":*");
        redisTemplate.delete(keys);
    }

    @PostConstruct
    public void init() {
        this.dtuInfoService = dtuInfoService1;
        this.checkingRulesService = checkingRulesService1;
        this.redisTemplate = redisTemplate1;
    }


//    /**
//     * 计算dtuId
//     *
//     * @param bytes
//     * @return
//     */
//    public static DtuInfo calculationDtuInfo(byte[] bytes) {
//        byte[] imeiBytes = new byte[Constant.IMEI_LENGTH];
//        System.arraycopy(bytes, 0, imeiBytes, 0, Constant.IMEI_LENGTH);
//        String imei = HexConvert.hexStringToString(HexConvert.BinaryToHexString(imeiBytes).replaceAll(" ", ""));
//        DtuInfo dtuInfo = dtuInfoService.findByImei(imei.trim());
//        return dtuInfo;
//    }

    /**
     * 必须检测是有用的数据才可以，如果不能够使用才不可以
     *
     * @param bytes
     * @return
     */
    public static boolean testingData(byte[] bytes) {
        //数据校检规则校验
        int bytesLength = bytes.length - Constant.IMEI_LENGTH;
        //查出所有符合此长度的规则
        List<CheckingRules> checkingRules = checkingRulesService.getByCommonLength(bytesLength);
        for (CheckingRules dataCheckingRule : checkingRules) {
            //使用crc16校验——不需要imei值
            boolean b = validCRC16(bytes, dataCheckingRule);
            if (!b) {
                log.error("bytes：{}校验通不过——crc16校验不过：{}", HexConvert.BinaryToHexString(bytes));
                return false;
            }
        }
        return true;
    }


    /**
     * crc16校验——校验7位bytes,最后两位为校验为
     *
     * @param bytes            收到byte[]信息
     * @param dataCheckingRule
     * @return
     */
    private static boolean validCRC16(byte[] bytes, CheckingRules dataCheckingRule) {
        //传感器地址
        String[] s = HexConvert.BinaryToHexString(bytes).split(" ");
        //校验位字符
        String checkDigitStr = "";
        ////有效字符串长度
        int useLength = dataCheckingRule.getCommonLength() - dataCheckingRule.getCrc16CheckDigitLength();
        for (int i = useLength; i < s.length; i++) {
            checkDigitStr = checkDigitStr + s[i];
        }
        //有效字符串
        byte[] useBytes = new byte[useLength];
        System.arraycopy(bytes, 0, useBytes, 0, useLength);  //数组截取
        return CRC16.getCRC3(useBytes).equalsIgnoreCase(checkDigitStr);
    }

    /**
     * 向dtu发送指令
     *
     * @param hex
     * @param ctx 通道上下文
     */
    public static void write(final String hex, ChannelHandlerContext ctx) {
        //加锁——根据通道加锁
        synchronized (ctx.channel()) {
            //重复指令一个轮询周期只发一次
            int time = Constant.INTERVAL_TIME_MAP.get(ctx.channel().id().toString()) == null ? Constant.INTERVAL_TIME : Constant.INTERVAL_TIME_MAP.get(ctx.channel().id().toString());
            Boolean pollingPeriod = redisTemplate.opsForValue().setIfAbsent(ctx.channel().id().toString() + "::" + hex, hex, Duration.ofMillis(time));
            if (!pollingPeriod) return;
            log.info("向通道：{} 发送指令：{}", ctx.channel().id().toString(), hex);
            //每个通道间隔一秒发送一条数据
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //netty需要用ByteBuf传输
            ByteBuf bufff = Unpooled.buffer();
            //对接需要16进制的byte[],不需要16进制字符串有空格
            bufff.writeBytes(HexConvert.hexStringToBytes(hex.replaceAll(" ", "")));
            ctx.writeAndFlush(bufff);
        }
    }
}
