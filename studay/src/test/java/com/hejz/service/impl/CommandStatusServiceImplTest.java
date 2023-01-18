package com.hejz.service.impl;

import com.hejz.common.Constant;
import com.hejz.entity.CommandStatus;
import com.hejz.service.CommandStatusService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommandStatusServiceImplTest {

    @Autowired
    CommandStatusService commandStatusService;
    @Autowired
    RedisTemplate redisTemplate;
    private String imei = "865328063321351";

    @Order(2)
    @Test
    void findByImei() {
        List<CommandStatus> commandStatus = commandStatusService.findAllByImei(imei);
        Assert.isTrue(commandStatus.size() == 1, "测试其元素为1个");
        Object o = redisTemplate.opsForValue().get(Constant.COMMAND_STATUS_CACHE_KEY + "::" + imei);
        Assert.isTrue(((ArrayList) o).size()==1, "测试其元素为1个");
    }

    @Order(3)
    @Test
    void findById() {
        CommandStatus commandStatus = commandStatusService.findById(1L);
        Assert.notNull(commandStatus, "commandStatus不为空值");
    }

    @Order(1)
    @Test
    void save() {
        commandStatusService.save(new CommandStatus(imei, 1L,new Date(),true));
        Object o = redisTemplate.opsForValue().get(Constant.SENSOR_CACHE_KEY + "::" + imei);
        Assert.isNull(o, "缓存中应该无值！");
    }

    @Order(4)
    @Test
    void update() {
        CommandStatus commandStatus = commandStatusService.findById(1L);
        commandStatus.setStatus(false);
        CommandStatus update = commandStatusService.update(commandStatus);
        Assert.isTrue(update.getStatus()==false, "修改值成功");
        Object o = redisTemplate.opsForValue().get(Constant.SENSOR_CACHE_KEY + "::" + imei);
        Assert.isNull(o, "缓存中应该无值！");
    }

    @Order(5)
    @Test
    void delete() {
        commandStatusService.delete(1L);
        Object o = redisTemplate.opsForValue().get(Constant.SENSOR_CACHE_KEY + "::" + imei);
        Assert.isNull(o, "缓存中应该无值！");
    }

}