package com.hejz.studay.service.impl;

import com.hejz.studay.DemoApplicationTests;
import com.hejz.studay.entity.DtuInfo;
import com.hejz.studay.entity.Sensor;
import com.hejz.studay.service.RedisCacheService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class RedisCacheServiceImplTest extends DemoApplicationTests {
    @Autowired
    private RedisCacheService redisCacheService;
    String imei = "865328063321359";
    @Test
    public void findDtuInfoByImei(){

        DtuInfo dtuInfoByImei = redisCacheService.getDtuInfoByImei(imei);
        System.out.println(dtuInfoByImei);
    }
    @Test
    public void getSensorByImei(){
        List<Sensor> sensorByImei = redisCacheService.getSensorByImei(imei);
        System.out.println(sensorByImei);
    }

}