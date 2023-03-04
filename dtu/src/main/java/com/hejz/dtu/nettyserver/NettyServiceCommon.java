package com.hejz.dtu.nettyserver;

import com.hejz.dtu.common.Constant;
import com.hejz.dtu.common.Result;
import com.hejz.dtu.entity.*;
import com.hejz.dtu.service.CommandService;
import com.hejz.dtu.service.DtuInfoService;
import com.hejz.dtu.service.InstructionDefinitionService;
import com.hejz.dtu.service.InstructionDefinitionStatusService;
import com.hejz.dtu.utils.CRC16;
import com.hejz.dtu.utils.HexConvert;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.Duration;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
    private InstructionDefinitionService instructionDefinitionService1;
    private static InstructionDefinitionService instructionDefinitionService;
    @Autowired
    private InstructionDefinitionStatusService instructionDefinitionStatusService1;
    private static InstructionDefinitionStatusService instructionDefinitionStatusService;
    @Autowired
    private CommandService commandService1;
    private static CommandService commandService;
    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate1;
    private static RedisTemplate redisTemplate;

    /**
     * 删除所有缓存和内存中map
     *
     * @param channel
     */
    public static void deleteKey(Channel channel) {
        Constant.CHANNELGROUP.remove(channel);
        Constant.INTERVAL_TIME_MAP.remove(channel.id().toString());
        Constant.SENSOR_DATA_BYTE_LIST_MAP.remove(channel.id().toString());
        Constant.THREE_RECORDS_MAP.remove(channel.id().toString() + "min");
        Constant.THREE_RECORDS_MAP.remove(channel.id().toString() + "max");
        Set<String> keys = redisTemplate.keys(Constant.CACHE_INSTRUCTIONS_THAT_NEED_TO_CONTINUE_PROCESSING_CACHE_KEY + "::" + channel.id().toString() + ":*");
        redisTemplate.delete(keys);
    }



    @PostConstruct
    public void init() {
        this.dtuInfoService = dtuInfoService1;
        this.instructionDefinitionService = instructionDefinitionService1;
        this.redisTemplate = redisTemplate1;
        this.instructionDefinitionStatusService = instructionDefinitionStatusService1;
        this.commandService = commandService1;
    }


    /**
     * 指令的地址值
     *
     * @param bytes   16进制指令
     * @return
     */
    public static Integer addressValueOfInstruction(byte[] bytes) {
        String hexStr = HexConvert.BinaryToHexString(bytes);
        String hex = "0x" + hexStr.substring(0, 2);
        return Integer.parseInt(hex.substring(2), 16);//从第2个字符开始截取
    }

    /**
     * 必须检测是有用的数据才可以，如果不能够使用才不可以
     *
     * @param bytes
     * @param channel
     * @return
     */
    public static boolean testingData(byte[] bytes, Channel channel) {
        AttributeKey<Long> key = AttributeKey.valueOf(Constant.CHANNEl_KEY);
        Long dtuId = channel.attr(key).get();
        //数据校检规则校验
        //查出所有符合此长度的规则
        DtuInfo dtuInfo = dtuInfoService.findById(dtuId);
        Set<CheckingRules> checkingRules = new HashSet<>();
        for (InstructionDefinition instructionDefinition : instructionDefinitionService.findAllByDtuInfo(dtuInfo)) {
            for (Command command : instructionDefinition.getCommands()) {
                checkingRules.add(command.getCheckingRules());
            }
        }
        for (CheckingRules dataCheckingRule : checkingRules) {
            //使用crc16校验——不需要imei值
            boolean b = validCRC16(bytes, dataCheckingRule);
            if (b) {
                return true;
            }
        }
        log.error("bytes：{}校验通不过——crc16校验不过：{}", HexConvert.BinaryToHexString(bytes));
        return false;
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
     * @param channel 通道
     */
    public static void write(final String hex, Channel channel) {
        //加锁——根据通道加锁
        synchronized (channel) {
            //重复指令一个轮询周期只发一次
            int time = Constant.INTERVAL_TIME_MAP.get(channel.id().toString()) == null ? Constant.INTERVAL_TIME : Constant.INTERVAL_TIME_MAP.get(channel.id().toString());
            Boolean pollingPeriod = redisTemplate.opsForValue().setIfAbsent(channel.id().toString() + "::" + hex, hex, Duration.ofMillis(time));
            if (!pollingPeriod) return;
            log.info("向通道：{} 发送指令：{}", channel.id().toString(), hex);
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
            channel.writeAndFlush(bufff);
        }
    }

    /**
     * 根据继电器定义命令发送继电器指令——可以任何通道发送，但是经过命令找到dtuId再找到通道发送的，与谁发送的命令无关
     *
     * @param instructionDefinition
     */
    public static Result sendRelayCommandAccordingToLayIds(InstructionDefinition instructionDefinition) {
        List<InstructionDefinitionStatus> instructionDefinitionStatuses = instructionDefinitionStatusService.findByInstructionDefinition(instructionDefinition);
        if (!instructionDefinitionStatuses.isEmpty()) {
            //log.error("当前已经是此指令，只能发送相反指令后再发送此指令！");
            return Result.error(500,"命令已经执行，请执行相反命令后才能执行！");
        }
        //如果三次————三倍时间
//        LocalDateTime beginTime = instructionDefinition.getSendCommandTime()==null?LocalDateTime.now():instructionDefinition.getSendCommandTime();
//        Duration duration = Duration.between(beginTime, LocalDateTime.now());
        //指令过期时间
//        if (duration.getSeconds() > Constant.INSTRUCTION_NUM*instructionDefinition.getSendCommandTime()) {
//            log.error("指令{}已经发了{}次，不再发送", instructionDefinition, Constant.INSTRUCTION_NUM);
//            return;
//        }
        //先判断channel是否存在，如果存在直接发，如果不存在通过dtuId找到chaannel再发，
        Channel channel = Constant.USER_CHANNEL.get(instructionDefinition.getDtuInfo().getId());
        //如果还在活动状态就直接发送指令
        if (channel != null) {
            Channel finalChannel1 = channel;
            instructionDefinition.getCommands().forEach(command -> {
                //缓存需要继续处理的指令，如果不再处理不缓存——为程序收到继电器信号（继电器发送什么信号接收到什么信号）能联系在一起
                write(command.getInstructions(), finalChannel1);
                //发送完指令后要延时发送下一个延时指令
                Command nextCommand = commandService.findById(command.getNextLevelInstructionId());
                finalChannel1.eventLoop().schedule(() -> {
                    log.info("通道==》{}开始延时任务，延时：{}秒", finalChannel1.id().toString(), nextCommand.getWaitTimeNextCommand());
                    NettyServiceCommon.write(nextCommand.getInstructions(), finalChannel1);
                }, nextCommand.getWaitTimeNextCommand(), TimeUnit.SECONDS);
            });
            //修改状态
            updateInstrctionDefintionStatus(instructionDefinition);
        } else {
            //不存在的话就找到任何一个活动的channel重新发一次
            if (Constant.CHANNELGROUP.isEmpty()) {
                log.error("没有活动的通道，消息未能发送成功！{}", instructionDefinition);
                return Result.error(500,"没有活动的通道，消息未能发送成功！");
            }
            for (Channel channel1 : Constant.CHANNELGROUP) {
                channel = channel1;
                break;
            }
            Channel finalChannel = channel;
            instructionDefinition.getCommands().stream().forEach(command -> {
                finalChannel.eventLoop().schedule(() -> {
                    log.info("从通道{}发送指令{}",finalChannel.id().toString(), instructionDefinition);
                    sendRelayCommandAccordingToLayIds(instructionDefinition);
                }, command.getWaitTimeNextCommand(), TimeUnit.SECONDS);
                return;
            });
        }
        return null;
    }

    private static void updateInstrctionDefintionStatus(InstructionDefinition instructionDefinition) {
        InstructionDefinition contraryInstructionDefinition=instructionDefinitionService.findContrary(instructionDefinition);
        if(contraryInstructionDefinition!=null){
            //查找相反指令的活动状态
            List<InstructionDefinitionStatus> contraryInstructionDefinitionStatuses = instructionDefinitionStatusService.findByInstructionDefinition(contraryInstructionDefinition);
            if(!contraryInstructionDefinitionStatuses.isEmpty()){
                for (InstructionDefinitionStatus definitionStatus : contraryInstructionDefinitionStatuses) {
                    definitionStatus.setStatus(false);
                    definitionStatus.setUpdateDate(new Date());
                    instructionDefinitionStatusService.save(definitionStatus);
                }
            }
        }
        //保存状态
        InstructionDefinitionStatus instructionDefinitionStatus = new InstructionDefinitionStatus();
        instructionDefinitionStatus.setStatus(true);
        instructionDefinitionStatus.setUpdateDate(new Date());
        instructionDefinitionStatus.setCreateDate(new Date());
        instructionDefinitionStatus.setDtuInfo(instructionDefinition.getDtuInfo());
        instructionDefinitionStatus.setInstructionDefinition(instructionDefinition);
        instructionDefinitionStatusService.save(instructionDefinitionStatus);
    }

}
