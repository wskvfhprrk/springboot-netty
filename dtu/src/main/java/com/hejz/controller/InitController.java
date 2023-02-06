package com.hejz.controller;

import com.hejz.common.Constant;
import com.hejz.enm.CommandTypeEnum;
import com.hejz.entity.CheckingRules;
import com.hejz.entity.Command;
import com.hejz.entity.DtuInfo;
import com.hejz.repository.*;
import com.hejz.service.CommandService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-11 00:16
 * @Description: 启动时初始化数据
 */
@RestController
@Api(tags = "初始化数据")
public class InitController {
    @Autowired
    SensorRepository sensorRepository;
    @Autowired
    RelayRepository relayRepository;
    @Autowired
    DtuInfoRepository dtuInfoRepository;
    @Autowired
    RelayDefinitionCommandRepository relayDefinitionCommandRepository;
    @Autowired
    CheckingRulesRepository checkingRulesRepository;
    @Autowired
    CommandService commandService;
    @Autowired
    RedisTemplate redisTemplate;

    @PostConstruct
    public void initData() {
        CheckingRules checkingRules = checkingRulesRepository.save(new CheckingRules("7位MODBUS协议111122", 7, 1, 1, 1, 2, 2));
        CheckingRules checkingRules1 = checkingRulesRepository.save(new CheckingRules("8位MODBUS协议1111222", 8, 1, 1, 2, 2, 2));
        //指令
        Command command1 = commandService.save(new Command("普通生产厂商", "继电器开关1关闭", "继电器开关1关闭", "03 05 00 00 00 00 CC 28", checkingRules1, CommandTypeEnum.RELAY_OPEN_CIRCUIT, "", null,80,null,true));
        Command command2 = commandService.save(new Command("普通生产厂商", "继电器开关1打开", "继电器开关1打开", "03 05 00 00 FF 00 8D D8", checkingRules1, CommandTypeEnum.RELAY_CLOSE_CIRCUIT, "", null,null,command1,true));
        Command command3 = commandService.save(new Command("普通生产厂商", "继电器开关2关闭", "继电器开关2关闭", "03 05 00 00 00 00 CC 28", checkingRules1, CommandTypeEnum.RELAY_OPEN_CIRCUIT, "", null,80,null,true));
        Command command4 = commandService.save(new Command("普通生产厂商", "继电器开关2打开", "继电器开关2打开", "03 05 00 00 FF 00 8D D8", checkingRules1, CommandTypeEnum.RELAY_OPEN_CIRCUIT, "", null,null,command3,true));
        Command command5 = commandService.save(new Command("普通生产厂商", "继电器开关3关闭", "继电器开关3关闭", "03 05 00 00 00 00 CC 28", checkingRules1, CommandTypeEnum.RELAY_OPEN_CIRCUIT, "", null,80,null,true));
        Command command6 = commandService.save(new Command("普通生产厂商", "继电器开关3打开", "继电器开关3打开", "03 05 00 00 FF 00 8D D8", checkingRules1, CommandTypeEnum.RELAY_CLOSE_CIRCUIT, "", null,null,command5,true));
        Command command7 = commandService.save(new Command("普通生产厂商", "继电器开关4关闭", "继电器开关4关闭", "03 05 00 00 00 00 CC 28", checkingRules1, CommandTypeEnum.RELAY_OPEN_CIRCUIT, "", null,80,null,true));
        Command command8 = commandService.save(new Command("普通生产厂商", "继电器开关4打开", "继电器开关4打开", "03 05 00 00 FF 00 8D D8", checkingRules1, CommandTypeEnum.RELAY_CLOSE_CIRCUIT, "", null,null,command6,true));
        Command command9 = commandService.save(new Command("普通生产厂商", "空气温度 ", "空气温度 ", "01 03 03 00 00 01 84 4E", checkingRules, CommandTypeEnum.SENSOR_SENDS_COMMAND, "D/10", "ºC",null,null,true));
        Command command10 = commandService.save(new Command("普通生产厂商", "空气湿度 ", "空气湿度 ", "01 03 03 01 00 01 D5 8E", checkingRules, CommandTypeEnum.SENSOR_SENDS_COMMAND, "D/10", "%",null,null,true));
        Command command11 = commandService.save(new Command("普通生产厂商", "土壤PH  ", "土壤PH  ", "02 03 02 03 00 01 75 81", checkingRules, CommandTypeEnum.SENSOR_SENDS_COMMAND, "D/10","",null,null,true));
        Command command12 = commandService.save(new Command("普通生产厂商", "土壤温度 ", "土壤温度 ", "02 03 02 00 00 01 85 81", checkingRules, CommandTypeEnum.SENSOR_SENDS_COMMAND, "D/100+5","ºC",null,null,true));
        Command command13 = commandService.save(new Command("普通生产厂商", "土壤湿度 ", "土壤湿度 ", "02 03 02 01 00 01 D4 41", checkingRules, CommandTypeEnum.SENSOR_SENDS_COMMAND, "D/100", "%",null,null,true));
        Command command14 = commandService.save(new Command("普通生产厂商", "土壤氮   ", "土壤氮   ", "02 03 02 04 00 01 C4 40", checkingRules, CommandTypeEnum.SENSOR_SENDS_COMMAND, "D/1","mg/L",null,null,true));
        Command command15 = commandService.save(new Command("普通生产厂商", "土壤磷   ", "土壤磷   ", "02 03 02 05 00 01 95 80", checkingRules, CommandTypeEnum.SENSOR_SENDS_COMMAND, "D/1","mg/L",null,null,true));
        Command command16 = commandService.save(new Command("普通生产厂商", "土壤钾   ", "土壤钾   ", "02 03 02 06 00 01 65 80", checkingRules, CommandTypeEnum.SENSOR_SENDS_COMMAND, "D/1","mg/L",null,null,true));
        Command command17 = commandService.save(new Command("普通生产厂商", "土壤电导率", "土壤电导率", "02 03 02 02 00 01 24 41", checkingRules, CommandTypeEnum.SENSOR_SENDS_COMMAND, "D/1","us/cm", null,null,true));


        Long imei = 865328063321359L;
        for (int i = 0; i < 30; i++) {
            start(String.valueOf(imei), i);
            imei++;
        }
        //清除所有缓存
        Set<String> keys = redisTemplate.keys(Constant.CHECKING_RULES_CACHE_KEY + ":*");
        redisTemplate.delete(keys);
        keys = redisTemplate.keys(Constant.RELAY_CACHE_KEY + "*");
        redisTemplate.delete(keys);
        keys = redisTemplate.keys(Constant.SENSOR_CACHE_KEY + ":*");
        redisTemplate.delete(keys);
        keys = redisTemplate.keys(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY + "*");
        redisTemplate.delete(keys);
        keys = redisTemplate.keys(Constant.DTU_INFO_CACHE_KEY + ":*");
        redisTemplate.delete(keys);
        keys = redisTemplate.keys(Constant.COMMAND_STATUS_CACHE_KEY + ":*");
        redisTemplate.delete(keys);
        keys = redisTemplate.keys(Constant.CACHE_INSTRUCTIONS_THAT_NEED_TO_CONTINUE_PROCESSING_CACHE_KEY + ":*");
        redisTemplate.delete(keys);
        keys = redisTemplate.keys(Constant.DTU_INFO_IMEI_CACHE_KEY + ":*");
        redisTemplate.delete(keys);
    }

    private void start(String imei, int i) {
        DtuInfo dtuInfo = new DtuInfo(imei, 89, 30000, false, "112222222");
        dtuInfoRepository.save(dtuInfo);

        //处理继电器信息
//        relayRepository.save(new Relay((long) (i + 1), 3, "第1个继电器", "03 05 00 00 FF 00 8D D8", "03 05 00 00 00 00 CC 28", "lcaolhost:8080/hello", "大棚电机双锁开关"));
//        relayRepository.save(new Relay((long) (i + 1), 3, "第2个继电器", "03 05 00 01 FF 00 DC 18", "03 05 00 01 00 00 9D E8", "lcaolhost:8080/hello", "大棚电机总开关"));
//        relayRepository.save(new Relay((long) (i + 1), 3, "第3个继电器", "03 05 00 02 FF 00 2C 18", "03 05 00 02 00 00 6D E8", "lcaolhost:8080/hello", "浇水电阀开关"));
//        relayRepository.save(new Relay((long) (i + 1), 3, "第4个继电器", "03 05 00 03 FF 00 7D D8", "03 05 00 03 00 00 3C 28", "lcaolhost:8080/hello", "备用"));
//        List<Relay> relays = relayRepository.findAlByDtuInfo((long) (i + 1)).stream().sorted(Comparator.comparing(Relay::getId)).collect(Collectors.toList());
        //处理编辑继电器命令的信息
//        relayDefinitionCommandRepository.save(new RelayDefinitionCommand((long) (i + 1), "重置通风指令", "停止打开和关闭通风电机指令", relays.get(1).getId() + "-0," + relays.get(0).getId() + "-0",  0L, 0L, 0L, InstructionTypeEnum.RESET_COMMAND));
//        Optional<RelayDefinitionCommand> relayDefinitionCommandOptional = relayDefinitionCommandRepository.findByDtuInfo((long) (i + 1)).stream().filter(r -> r.getName().equals("重置通风指令")).findFirst();
//        relayDefinitionCommandRepository.save(new RelayDefinitionCommand((long) (i + 1), "打开通风指令", "打开左右通风", relays.get(1).getId() + "-1," + relays.get(0).getId() + "-0", 80000L, relayDefinitionCommandOptional.get().getId(), i * 3 + 3L,InstructionTypeEnum.OPEN_VENTILATION));
//        relayDefinitionCommandRepository.save(new RelayDefinitionCommand((long) (i + 1), "关闭通风指令", "关闭左右通风", relays.get(1).getId() + "-1," + relays.get(0).getId() + "-1",  80000L, relayDefinitionCommandOptional.get().getId(), i * 3 + 2L,InstructionTypeEnum.CLOSE_VENTILATION));
//        if (!relayDefinitionCommandOptional.isPresent()) return;
//        //处理感应器信息
//        List<RelayDefinitionCommand> relayDefinitionCommands = relayDefinitionCommandRepository.findByDtuInfo((long) (i + 1));
//        Optional<RelayDefinitionCommand> open = relayDefinitionCommands.stream().filter(r -> r.getName().equals("打开通风指令")).findFirst();
//        if (!open.isPresent()) return;
//        Optional<RelayDefinitionCommand> close = relayDefinitionCommands.stream().filter(r -> r.getName().equals("关闭通风指令")).findFirst();
//        sensorRepository.save(new Sensor((long) (i + 1), 1, "空气温度 ", "01 03 03 00 00 01 84 4E", "D/10", "ºC", 25, 15, open.get().getId(), close.get().getId()));
//        sensorRepository.save(new Sensor((long) (i + 1), 1, "空气湿度 ", "01 03 03 01 00 01 D5 8E", "D/10", "%", 90, 70, 0L, 0L));
//        sensorRepository.save(new Sensor((long) (i + 1), 2, "土壤PH  ", "02 03 02 03 00 01 75 81", "D/10", "", 9, 6, 0L, 0L));
//        sensorRepository.save(new Sensor((long) (i + 1), 2, "土壤温度 ", "02 03 02 00 00 01 85 81", "D/100+5", "ºC", 25, 25, 0L, 0L));
//        sensorRepository.save(new Sensor((long) (i + 1), 2, "土壤湿度 ", "02 03 02 01 00 01 D4 41", "D/100", "%", 100, 80, 0L, 0L));
//        sensorRepository.save(new Sensor((long) (i + 1), 2, "土壤氮   ", "02 03 02 04 00 01 C4 40", "D/1", "mg/L", 100, 50, 0L, 0L));
//        sensorRepository.save(new Sensor((long) (i + 1), 2, "土壤磷   ", "02 03 02 05 00 01 95 80", "D/1", "mg/L", 100, 50, 0L, 0L));
//        sensorRepository.save(new Sensor((long) (i + 1), 2, "土壤钾   ", "02 03 02 06 00 01 65 80", "D/1", "mg/L", 100, 50, 0L, 0L));
//        sensorRepository.save(new Sensor((long) (i + 1), 2, "土壤电导率", "02 03 02 02 00 01 24 41", "D/1", "us/cm", 250, 80, 0L, 0L));
//        //处理dtu信息


    }



}