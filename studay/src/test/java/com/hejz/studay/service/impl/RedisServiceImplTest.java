package com.hejz.studay.service.impl;

import com.hejz.studay.entity.DtuInfo;
import com.hejz.studay.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;



public class RedisServiceImplTest {
    @Autowired
    private RedisService redisService;

    @Test
    public void findDtuInfoByImei(){
        DtuInfo dtuInfoByImei = redisService.findDtuInfoByImei("865328063321359");
        System.out.println(dtuInfoByImei);
    }

}