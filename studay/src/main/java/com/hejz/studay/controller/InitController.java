package com.hejz.studay.controller;

import com.hejz.studay.entity.DtuInfo;
import com.hejz.studay.entity.Relay;
import com.hejz.studay.entity.RelayDefinitionCommand;
import com.hejz.studay.entity.Sensor;
import com.hejz.studay.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-11 00:16
 * @Description: 启动时初始化数据
 */
@RestController
public class InitController {
    @Autowired
    SensorRepository sensorRepository;
    @Autowired
    RelayRepository relayRepository;
    @Autowired
    DtuInfoRepository dtuInfoRepository;
    @Autowired
    RelayDefinitionCommandRepository relayDefinitionCommandRepository;
    @PostConstruct
    public void initData(){
        String imei = "865328063321359";
        //处理继电器信息
        relayRepository.save(new Relay(1L, imei, 3, "第1个继电器", "03 05 00 00 FF 00 8D D8", "03 05 00 00 00 00 CC 28",  "lcaolhost:8080/hello", "大棚电机双锁开关"));
        relayRepository.save(new Relay(2L, imei, 3, "第2个继电器", "03 05 00 01 FF 00 DC 18", "03 05 00 01 00 00 9D E8",  "lcaolhost:8080/hello", "大棚电机总开关"));
        relayRepository.save(new Relay(3L, imei, 3, "第3个继电器", "03 05 00 02 FF 00 2C 18", "03 05 00 02 00 00 6D E8",  "lcaolhost:8080/hello", "浇水电阀开关"));
        relayRepository.save(new Relay(4L, imei, 3, "第4个继电器", "03 05 00 03 FF 00 7D D8", "03 05 00 03 00 00 3C 28",  "lcaolhost:8080/hello", "备用"));
        //处理编辑继电器命令的信息
        relayDefinitionCommandRepository.save(new RelayDefinitionCommand(imei,"关闭打开大棚指令","停止大棚电机指令","2-0,1-0",false,0L,0L));
        Optional<RelayDefinitionCommand> relayDefinitionCommandOptional = relayDefinitionCommandRepository.getAllByImei(imei).stream().filter(r->r.getName().equals("关闭打开大棚指令")).findFirst();
        relayDefinitionCommandRepository.save(new RelayDefinitionCommand(imei,"打开大棚指令","打开左右大棚","2-1,1-0",true,30000L,relayDefinitionCommandOptional.get().getId()));
        relayDefinitionCommandRepository.save(new RelayDefinitionCommand(imei,"关闭大棚指令","关闭左右大棚","2-1,1-1",true,30000L,relayDefinitionCommandOptional.get().getId()));
        if(!relayDefinitionCommandOptional.isPresent())return;
        //处理感应器信息
        List<RelayDefinitionCommand> relayDefinitionCommands = relayDefinitionCommandRepository.getAllByImei(imei);
        Optional<RelayDefinitionCommand> open = relayDefinitionCommands.stream().filter(r -> r.getName().equals("打开大棚指令")).findFirst();
        if(!open.isPresent())return;
        Optional<RelayDefinitionCommand> close = relayDefinitionCommands.stream().filter(r -> r.getName().equals("关闭大棚指令")).findFirst();
        sensorRepository.save(new Sensor(1L, imei, 1, "空气温度 ", "01 03 03 00 00 01 84 4E", "D/10", "ºC", 25, 20, open.get().getId(), close.get().getId()));
        sensorRepository.save(new Sensor(2L, imei, 1, "空气湿度 ", "01 03 03 01 00 01 D5 8E", "D/10", "%", 90, 70, 0L, 0L));
        sensorRepository.save(new Sensor(3L, imei, 2, "土壤PH  ", "02 03 02 03 00 01 75 81", "D/10", "", 9, 6, 0L, 0L));
        sensorRepository.save(new Sensor(4L, imei, 2, "土壤温度 ", "02 03 02 00 00 01 85 81", "D/100+5", "ºC", 25, 25, 0L, 0L));
        sensorRepository.save(new Sensor(5L, imei, 2, "土壤湿度 ", "02 03 02 01 00 01 D4 41", "D/100", "%", 100, 80, 0L, 0L));
        sensorRepository.save(new Sensor(6L, imei, 2, "土壤氮   ", "02 03 02 04 00 01 C4 40", "D/1", "mg/L", 100, 50, 0L, 0L));
        sensorRepository.save(new Sensor(7L, imei, 2, "土壤磷   ", "02 03 02 05 00 01 95 80", "D/1", "mg/L", 100, 50, 0L, 0L));
        sensorRepository.save(new Sensor(8L, imei, 2, "土壤钾   ", "02 03 02 06 00 01 65 80", "D/1", "mg/L", 100, 50, 0L, 0L));
        sensorRepository.save(new Sensor(9L, imei, 2, "土壤电导率", "02 03 02 02 00 01 24 41", "D/1", "us/cm", 250, 80, 0L, 0L));
        //处理dtu信息
        DtuInfo dtuInfo = new DtuInfo();
        dtuInfo.setId(1L);
        dtuInfo.setImei(imei);
        dtuInfo.setImeiLength(15);
        dtuInfo.setRelayLength(8);
        dtuInfo.setSensorLength(7);
        dtuInfo.setGroupIntervalTime(5000);
        dtuInfo.setHeartbeatLength(2);
        dtuInfo.setRegistrationLength(89);
        dtuInfo.setAutomaticAdjustment(true);
        dtuInfoRepository.save(dtuInfo);
    }

}
