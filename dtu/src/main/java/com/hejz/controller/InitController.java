package com.hejz.controller;

import com.hejz.common.Constant;
import com.hejz.entity.*;
import com.hejz.repository.*;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    RedisTemplate redisTemplate;

    @PostConstruct
    public void initData() {
        checkingRulesRepository.save(new CheckingRules("7位MODBUS协议111122", 7, 1, 1, 1, 2, 2));
        checkingRulesRepository.save(new CheckingRules("8位MODBUS协议1111222", 8, 1, 1, 2, 2, 2));
        Long imei = 865328063321359L;
        for (int i = 0; i < 10; i++) {
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
        //处理继电器信息
        relayRepository.save(new Relay((long) (i + 1), 3, "第1个继电器", "03 05 00 00 FF 00 8D D8", "03 05 00 00 00 00 CC 28", "lcaolhost:8080/hello", "大棚电机双锁开关"));
        relayRepository.save(new Relay((long) (i + 1), 3, "第2个继电器", "03 05 00 01 FF 00 DC 18", "03 05 00 01 00 00 9D E8", "lcaolhost:8080/hello", "大棚电机总开关"));
        relayRepository.save(new Relay((long) (i + 1), 3, "第3个继电器", "03 05 00 02 FF 00 2C 18", "03 05 00 02 00 00 6D E8", "lcaolhost:8080/hello", "浇水电阀开关"));
        relayRepository.save(new Relay((long) (i + 1), 3, "第4个继电器", "03 05 00 03 FF 00 7D D8", "03 05 00 03 00 00 3C 28", "lcaolhost:8080/hello", "备用"));
        List<Relay> relays = relayRepository.findAlByDtuId((long) (i + 1)).stream().sorted(Comparator.comparing(Relay::getId)).collect(Collectors.toList());
        //处理编辑继电器命令的信息
        relayDefinitionCommandRepository.save(new RelayDefinitionCommand((long) (i + 1), "重置大棚指令", "停止大棚电机指令", relays.get(1).getId() + "-0," + relays.get(0).getId() + "-0", false, 3000L, 0L, 0L));
        Optional<RelayDefinitionCommand> relayDefinitionCommandOptional = relayDefinitionCommandRepository.findByDtuId((long) (i + 1)).stream().filter(r -> r.getName().equals("重置大棚指令")).findFirst();
        relayDefinitionCommandRepository.save(new RelayDefinitionCommand((long) (i + 1), "打开大棚指令", "打开左右大棚", relays.get(1).getId() + "-1," + relays.get(0).getId() + "-0", true, 3000L, relayDefinitionCommandOptional.get().getId(), i * 3 + 3L));
        relayDefinitionCommandRepository.save(new RelayDefinitionCommand((long) (i + 1), "关闭大棚指令", "关闭左右大棚", relays.get(1).getId() + "-1," + relays.get(0).getId() + "-1", true, 3000L, relayDefinitionCommandOptional.get().getId(), i * 3 + 2L));
        if (!relayDefinitionCommandOptional.isPresent()) return;
        //处理感应器信息
        List<RelayDefinitionCommand> relayDefinitionCommands = relayDefinitionCommandRepository.findByDtuId((long) (i + 1));
        Optional<RelayDefinitionCommand> open = relayDefinitionCommands.stream().filter(r -> r.getName().equals("打开大棚指令")).findFirst();
        if (!open.isPresent()) return;
        Optional<RelayDefinitionCommand> close = relayDefinitionCommands.stream().filter(r -> r.getName().equals("关闭大棚指令")).findFirst();
        sensorRepository.save(new Sensor((long) (i + 1), 1, "空气温度 ", "01 03 03 00 00 01 84 4E", "D/10", "ºC", 25, 15, open.get().getId(), close.get().getId()));
        sensorRepository.save(new Sensor((long) (i + 1), 1, "空气湿度 ", "01 03 03 01 00 01 D5 8E", "D/10", "%", 90, 70, 0L, 0L));
        sensorRepository.save(new Sensor((long) (i + 1), 2, "土壤PH  ", "02 03 02 03 00 01 75 81", "D/10", "", 9, 6, 0L, 0L));
        sensorRepository.save(new Sensor((long) (i + 1), 2, "土壤温度 ", "02 03 02 00 00 01 85 81", "D/100+5", "ºC", 25, 25, 0L, 0L));
        sensorRepository.save(new Sensor((long) (i + 1), 2, "土壤湿度 ", "02 03 02 01 00 01 D4 41", "D/100", "%", 100, 80, 0L, 0L));
        sensorRepository.save(new Sensor((long) (i + 1), 2, "土壤氮   ", "02 03 02 04 00 01 C4 40", "D/1", "mg/L", 100, 50, 0L, 0L));
        sensorRepository.save(new Sensor((long) (i + 1), 2, "土壤磷   ", "02 03 02 05 00 01 95 80", "D/1", "mg/L", 100, 50, 0L, 0L));
        sensorRepository.save(new Sensor((long) (i + 1), 2, "土壤钾   ", "02 03 02 06 00 01 65 80", "D/1", "mg/L", 100, 50, 0L, 0L));
        sensorRepository.save(new Sensor((long) (i + 1), 2, "土壤电导率", "02 03 02 02 00 01 24 41", "D/1", "us/cm", 250, 80, 0L, 0L));
        //处理dtu信息
        dtuInfoRepository.save(new DtuInfo(imei, 89, 15, "3-2", "1-1,2-1", 2, 30000, true, true,"112222222"));
    }

}
