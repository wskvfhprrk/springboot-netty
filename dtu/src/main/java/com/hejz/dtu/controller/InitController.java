package com.hejz.dtu.controller;

import com.hejz.dtu.common.Constant;
import com.hejz.dtu.enm.CommandTypeEnum;
import com.hejz.dtu.enm.DictionaryTypeEnum;
import com.hejz.dtu.enm.InstructionTypeEnum;
import com.hejz.dtu.entity.*;
import com.hejz.dtu.repository.*;
import com.hejz.dtu.service.CommandService;
import com.hejz.dtu.service.DictionaryService;
import com.hejz.dtu.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
//此处不能删除——不然系统不能自动部署
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashSet;
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
    DtuInfoRepository dtuInfoRepository;
    @Autowired
    InstructionDefinitionRepository instructionDefinitionRepository;
    @Autowired
    CheckingRulesRepository checkingRulesRepository;
    @Autowired
    CommandService commandService;
    @Autowired
    DictionaryService dictionaryService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    UserRepository userRepository;

    @PostConstruct
    public void initData() {
        start();
        //清除所有缓存
        Set<String> keys = redisTemplate.keys(Constant.SENSOR_CACHE_KEY + ":*");
        redisTemplate.delete(keys);
        keys = redisTemplate.keys(Constant.DTU_INFO_CACHE_KEY + ":*");
        redisTemplate.delete(keys);
        keys = redisTemplate.keys(Constant.CACHE_INSTRUCTIONS_THAT_NEED_TO_CONTINUE_PROCESSING_CACHE_KEY + ":*");
        redisTemplate.delete(keys);
        keys = redisTemplate.keys(Constant.DTU_INFO_IMEI_CACHE_KEY + ":*");
        redisTemplate.delete(keys);
        keys = redisTemplate.keys(Constant.INSTRUCTION_DEFINITION_CACHE_KEY + ":*");
        redisTemplate.delete(keys);
    }

    private void start() {
        //字典添加内容
        Dictionary dictionary = dictionaryService.save(new Dictionary("顶级", new Date(), "顶级", true,  "项级", "TOP_LEVEL", 1, DictionaryTypeEnum.TOP_LEVEL, new Dictionary(1L)));
        Dictionary dictionary1 = dictionaryService.save(new Dictionary("字典", new Date(), "字典类型", true, "一级", "TOP_LEVEL", 1, DictionaryTypeEnum.CLASS_A, dictionary));
        dictionaryService.save(new Dictionary("字典",new Date(),"字典类型",true,"一级","CLASS_A",1, DictionaryTypeEnum.SECOND_LEVEL,dictionary1));
        dictionaryService.save(new Dictionary("字典",new Date(),"字典类型",true,"二级","SECOND_LEVEL",2, DictionaryTypeEnum.SECOND_LEVEL,dictionary1));

        CheckingRules checkingRules = checkingRulesRepository.save(new CheckingRules("7位MODBUS协议111122", 7, 1, 1, 1, 2, 2,true));
        CheckingRules checkingRules1 = checkingRulesRepository.save(new CheckingRules("8位MODBUS协议1111222", 8, 1, 1, 2, 2, 2,true));
        //指令
        Command command1 = commandService.save(new Command("普通生产厂商", "继电器开关1关闭", "继电器开关1关闭", "03 05 00 00 00 00 CC 28", checkingRules1, CommandTypeEnum.RELAY_OPEN_CIRCUIT, null, 80, null, true));
        Command command2 = commandService.save(new Command("普通生产厂商", "继电器开关1打开", "继电器开关1打开", "03 05 00 00 FF 00 8D D8", checkingRules1, CommandTypeEnum.RELAY_CLOSE_CIRCUIT,  null, null, command1.getId(), true));
        Command command3 = commandService.save(new Command("普通生产厂商", "继电器开关2关闭", "继电器开关2关闭", "03 05 00 01 00 00 9D E8", checkingRules1, CommandTypeEnum.RELAY_OPEN_CIRCUIT,  null, 80, null, true));
        Command command4 = commandService.save(new Command("普通生产厂商", "继电器开关2打开", "继电器开关2打开", "03 05 00 01 FF 00 DC 18", checkingRules1, CommandTypeEnum.RELAY_OPEN_CIRCUIT,  null, null, command3.getId(), true));
        Command command5 = commandService.save(new Command("普通生产厂商", "继电器开关3关闭", "继电器开关3关闭", "03 05 00 02 00 00 6D E8", checkingRules1, CommandTypeEnum.RELAY_OPEN_CIRCUIT,  null, null, null, true));
        Command command6 = commandService.save(new Command("普通生产厂商", "继电器开关3打开", "继电器开关3打开", "03 05 00 02 FF 00 2C 18", checkingRules1, CommandTypeEnum.RELAY_CLOSE_CIRCUIT,  null, null, null, true));
        Command command7 = commandService.save(new Command("普通生产厂商", "继电器开关4关闭", "继电器开关4关闭", "03 05 00 03 00 00 3C 28", checkingRules1, CommandTypeEnum.RELAY_OPEN_CIRCUIT,  null, 80, null, true));
        Command command8 = commandService.save(new Command("普通生产厂商", "继电器开关4打开", "继电器开关4打开", "03 05 00 03 FF 00 7D D8", checkingRules1, CommandTypeEnum.RELAY_CLOSE_CIRCUIT,  null, null, command6.getId(), true));
        Command command9 = commandService.save(new Command("普通生产厂商", "空气温度 ", "空气温度 ", "01 03 03 00 00 01 84 4E", checkingRules, CommandTypeEnum.SENSOR_SENDS_COMMAND, "D/10", null, null, true));
        Command command10 = commandService.save(new Command("普通生产厂商", "空气湿度 ", "空气湿度 ", "01 03 03 01 00 01 D5 8E", checkingRules, CommandTypeEnum.SENSOR_SENDS_COMMAND, "D/10", null, null, true));
        Command command11 = commandService.save(new Command("普通生产厂商", "土壤PH  ", "土壤PH  ", "02 03 02 03 00 01 75 81", checkingRules, CommandTypeEnum.SENSOR_SENDS_COMMAND, "D/10",null, null, true));
        Command command12 = commandService.save(new Command("普通生产厂商", "土壤温度 ", "土壤温度 ", "02 03 02 00 00 01 85 81", checkingRules, CommandTypeEnum.SENSOR_SENDS_COMMAND, "D/100+5", null, null, true));
        Command command13 = commandService.save(new Command("普通生产厂商", "土壤湿度 ", "土壤湿度 ", "02 03 02 01 00 01 D4 41", checkingRules, CommandTypeEnum.SENSOR_SENDS_COMMAND, "D/100", null, null, true));
        Command command14 = commandService.save(new Command("普通生产厂商", "土壤氮   ", "土壤氮   ", "02 03 02 04 00 01 C4 40", checkingRules, CommandTypeEnum.SENSOR_SENDS_COMMAND, "D/1", null, null, true));
        Command command15 = commandService.save(new Command("普通生产厂商", "土壤磷   ", "土壤磷   ", "02 03 02 05 00 01 95 80", checkingRules, CommandTypeEnum.SENSOR_SENDS_COMMAND, "D/1", null, null, true));
        Command command16 = commandService.save(new Command("普通生产厂商", "土壤钾   ", "土壤钾   ", "02 03 02 06 00 01 65 80", checkingRules, CommandTypeEnum.SENSOR_SENDS_COMMAND, "D/1", null, null, true));
        Command command17 = commandService.save(new Command("普通生产厂商", "土壤电导率", "土壤电导率", "02 03 02 02 00 01 24 41", checkingRules, CommandTypeEnum.SENSOR_SENDS_COMMAND, "D/1", null, null, true));
        //imei值
        Long imei = 865328063321359L;
        for (int i = 0; i < 30; i++) {
            DtuInfo dtuInfo = dtuInfoRepository.save(new DtuInfo(imei.toString(), 89, 120000, true, "1,1,2,2,2,2,2,2,2"));

            //处理编辑继电器命令的信息
            Set<Command> set = new HashSet<>();
            set.add(command4);
            InstructionDefinition instructionDefinition = instructionDefinitionRepository.save(new InstructionDefinition(dtuInfo, "打开通风", "打开通风", InstructionTypeEnum.OPEN_VENTILATION, set));
            set.clear();
            set.add(command2);
            set.add(command4);
            InstructionDefinition instructionDefinition1 = instructionDefinitionRepository.save(new InstructionDefinition(dtuInfo, "关闭通风", "关闭通风", InstructionTypeEnum.CLOSE_VENTILATION, set));
            set.clear();
            set.add(command6);
            InstructionDefinition instructionDefinition2 = instructionDefinitionRepository.save(new InstructionDefinition(dtuInfo, "打开浇水阀", "打开浇水阀", InstructionTypeEnum.TURN_ON_WATERING, set));
            set.clear();
            set.add(command5);
            InstructionDefinition instructionDefinition3 = instructionDefinitionRepository.save(new InstructionDefinition(dtuInfo, "关闭浇水阀", "关闭浇水阀", InstructionTypeEnum.TURN_OFF_WATERING, set));
            set.clear();
            set.add(command9);
            InstructionDefinition instructionDefinition4 = instructionDefinitionRepository.save(new InstructionDefinition(dtuInfo, "空气温度", "空气温度", InstructionTypeEnum.SENSOR_COMMAND, set));
            set.clear();
            set.add(command10);
            InstructionDefinition instructionDefinition5 = instructionDefinitionRepository.save(new InstructionDefinition(dtuInfo, "空气湿度", "空气湿度", InstructionTypeEnum.SENSOR_COMMAND, set));
            set.clear();
            set.add(command11);
            InstructionDefinition instructionDefinition6 = instructionDefinitionRepository.save(new InstructionDefinition(dtuInfo, "土壤PH", "土壤PH", InstructionTypeEnum.SENSOR_COMMAND, set));
            set.clear();
            set.add(command12);
            InstructionDefinition instructionDefinition7 = instructionDefinitionRepository.save(new InstructionDefinition(dtuInfo, "土壤温度", "土壤温度", InstructionTypeEnum.SENSOR_COMMAND, set));
            set.clear();
            set.add(command13);
            InstructionDefinition instructionDefinition8 = instructionDefinitionRepository.save(new InstructionDefinition(dtuInfo, "土壤湿度", "土壤湿度", InstructionTypeEnum.SENSOR_COMMAND, set));
            set.clear();
            set.add(command14);
            InstructionDefinition instructionDefinition9 = instructionDefinitionRepository.save(new InstructionDefinition(dtuInfo, "土壤氮", "土壤氮", InstructionTypeEnum.SENSOR_COMMAND, set));
            set.clear();
            set.add(command15);
            InstructionDefinition instructionDefinition10 = instructionDefinitionRepository.save(new InstructionDefinition(dtuInfo, "土壤磷", "土壤磷", InstructionTypeEnum.SENSOR_COMMAND, set));
            set.clear();
            set.add(command16);
            InstructionDefinition instructionDefinition11 = instructionDefinitionRepository.save(new InstructionDefinition(dtuInfo, "土壤钾", "土壤钾", InstructionTypeEnum.SENSOR_COMMAND, set));
            set.clear();
            set.add(command17);
            InstructionDefinition instructionDefinition12 = instructionDefinitionRepository.save(new InstructionDefinition(dtuInfo, "土壤电导率", "土壤电导率", InstructionTypeEnum.SENSOR_COMMAND, set));
//        //感应器信息
            sensorRepository.save(new Sensor(dtuInfo,"空气温度",25,20,instructionDefinition,instructionDefinition1,command9,1,"ºC"));
            sensorRepository.save(new Sensor(dtuInfo,"空气湿度",25,15,null,null,command10,2,"%"));
            sensorRepository.save(new Sensor(dtuInfo,"土壤PH",25,15,null,null,command11,3, ""));
            sensorRepository.save(new Sensor(dtuInfo,"土壤温度",25,15,null,null,command12,4,"ºC"));
            sensorRepository.save(new Sensor(dtuInfo,"土壤湿度",100,15,instructionDefinition2,instructionDefinition3,command13,5,"%"));
            sensorRepository.save(new Sensor(dtuInfo,"土壤氮",25,15,null,null,command14,6, "mg/L"));
            sensorRepository.save(new Sensor(dtuInfo,"土壤磷",25,15,null,null,command15,7, "mg/L"));
            sensorRepository.save(new Sensor(dtuInfo,"土壤钾",25,15,null,null,command15,8, "mg/L"));
            sensorRepository.save(new Sensor(dtuInfo,"土壤电导率",25,15,null,null,command16,9,"us/cm"));

            imei++;
        }
        userRepository.save(new User(18,"admin",null));
    }



}