package com.hejz.service.impl;

import com.hejz.common.Constant;
import com.hejz.dto.DtuInfoDto;
import com.hejz.dto.DtuInfoUpdateDto;
import com.hejz.entity.DtuInfo;
import com.hejz.service.DtuInfoService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DtuInfoServiceImplTest {

    @Autowired
    DtuInfoService dtuInfoService;
    @Autowired
    RedisTemplate redisTemplate;
    private String imei = "865328063323359";

    @Order(5)
    @Test
    void findByImei() {
        DtuInfo dtuInfo = dtuInfoService.findByImei(imei);
        Assert.notNull(dtuInfo, "数据库中有值");
        Object o = redisTemplate.opsForValue().get(Constant.DTU_INFO_CACHE_KEY + "::" + imei);
        Assert.notNull(o, "缓存中有值！");
    }

    @Order(3)
    @Test
    void findById() {
        DtuInfo dtuInfo = dtuInfoService.findById(1L);
        Assert.notNull(dtuInfo, "dtuInfo不为空值");
    }

    @Order(1)
    @Test
    void save() {
//        dtuInfoService.save(new DtuInfoDto(imei,1,1,"1","1",1,1,true,true,"1"));
//        Object o = redisTemplate.opsForValue().get(Constant.DTU_INFO_CACHE_KEY + "::" + imei);
//        Assert.isNull(o, "缓存中应该无值！");
    }

    @Order(2)
    @Test
    void update() {
        DtuInfo dtuInfo = dtuInfoService.findById(1L);
        dtuInfo.setNoImei(false);
        DtuInfoUpdateDto dto=new DtuInfoUpdateDto();
        BeanUtils.copyProperties(dtuInfo,dto);
        DtuInfo update = dtuInfoService.update(dto);
        Assert.isTrue(!update.getNoImei(), "修改值成功");
        Object o = redisTemplate.opsForValue().get(Constant.DTU_INFO_CACHE_KEY + "::" + imei);
        Assert.isNull(o, "缓存中应该无值！");
    }

    @Order(4)
    @Test
    void delete() {
        dtuInfoService.delete(1L);
        Object o = redisTemplate.opsForValue().get(Constant.DTU_INFO_CACHE_KEY + "::" + imei);
        Assert.isNull(o, "缓存中应该无值！");
        DtuInfo dtuInfos = dtuInfoService.findByImei(imei);
//        Assert.isTrue(dtuInfos.size() == 1, "测试其元素为1个");
        Object o1 = redisTemplate.opsForValue().get(Constant.DTU_INFO_CACHE_KEY + "::" + imei);
        Assert.notNull(o1, "缓存中有值！");
    }

}