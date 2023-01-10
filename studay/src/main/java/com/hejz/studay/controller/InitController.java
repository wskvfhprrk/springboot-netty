package com.hejz.studay.controller;

import com.hejz.studay.entity.DtuInfo;
import com.hejz.studay.entity.Relay;
import com.hejz.studay.entity.Sensor;
import com.hejz.studay.repository.DtuInfoRepository;
import com.hejz.studay.repository.RelayRepository;
import com.hejz.studay.repository.SensorDataDbRepository;
import com.hejz.studay.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

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
    @PostConstruct
    public void initData(){
        sensorRepository.save(new Sensor(1L, "865328063321359", 1, "空气温度 ", "01 03 03 00 00 01 84 4E", "D/10", "ºC", 25, 15, "2-1,1-1", "2-1,1-0"));
        sensorRepository.save(new Sensor(2L, "865328063321359", 1, "空气湿度 ", "01 03 03 01 00 01 D5 8E", "D/10", "%", 90, 70, "0", "0"));
        sensorRepository.save(new Sensor(3L, "865328063321359", 2, "土壤PH  ", "02 03 02 03 00 01 75 81", "D/10", "", 9, 6, "0", "0"));
        sensorRepository.save(new Sensor(4L, "865328063321359", 2, "土壤温度 ", "02 03 02 00 00 01 85 81", "D/100+5", "ºC", 25, 25, "0", "0"));
        sensorRepository.save(new Sensor(5L, "865328063321359", 2, "土壤湿度 ", "02 03 02 01 00 01 D4 41", "D/100", "%", 100, 80, "0", "0"));
        sensorRepository.save(new Sensor(6L, "865328063321359", 2, "土壤氮   ", "02 03 02 04 00 01 C4 40", "D/1", "mg/L", 100, 50, "0", "0"));
        sensorRepository.save(new Sensor(7L, "865328063321359", 2, "土壤磷   ", "02 03 02 05 00 01 95 80", "D/1", "mg/L", 100, 50, "0", "0"));
        sensorRepository.save(new Sensor(8L, "865328063321359", 2, "土壤钾   ", "02 03 02 06 00 01 65 80", "D/1", "mg/L", 100, 50, "0", "0"));
        sensorRepository.save(new Sensor(9L, "865328063321359", 2, "土壤电导率", "02 03 02 02 00 01 24 41", "D/1", "us/cm", 250, 80, "0", "0"));
        relayRepository.save(new Relay(1L, "865328063321359", 3, "棚双锁开关", "03 05 00 00 FF 00 8D D8", "03 05 00 00 00 00 CC 28", 30000L, "lcaolhost:8080/hello", "棚双锁开关"));
        relayRepository.save(new Relay(2L, "865328063321359", 3, "大棚总开关", "03 05 00 01 FF 00 DC 18", "03 05 00 01 00 00 9D E8", 30000L, "lcaolhost:8080/hello", "大棚总开关"));
        relayRepository.save(new Relay(3L, "865328063321359", 3, "", "03 05 00 02 FF 00 2C 18", "03 05 00 02 00 00 6D E8", 0L, "lcaolhost:8080/hello", ""));
        relayRepository.save(new Relay(4L, "865328063321359", 3, "", "03 05 00 03 FF 00 7D D8", "03 05 00 03 00 00 3C 28", 0L, "lcaolhost:8080/hello", ""));
        DtuInfo dtuInfo = new DtuInfo();
        dtuInfo.setId(1L);
        dtuInfo.setImei("865328063321359");
        dtuInfo.setImeiLength(15);
        dtuInfo.setRelayLength(8);
        dtuInfo.setSensorLength(7);
        dtuInfo.setGroupIntervalTime(2000);
        dtuInfo.setHeartbeatLength(2);
        dtuInfo.setRegistrationLength(89);
        dtuInfo.setAutomaticAdjustment(true);
        dtuInfoRepository.save(dtuInfo);
    }

}
