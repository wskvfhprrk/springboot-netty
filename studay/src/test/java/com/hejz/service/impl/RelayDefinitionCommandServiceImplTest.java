package com.hejz.service.impl;

import com.hejz.common.Constant;
import com.hejz.entity.RelayDefinitionCommand;
import com.hejz.service.RelayDefinitionCommandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
class RelayDefinitionCommandServiceImplTest {
    
    @Autowired
    RelayDefinitionCommandService relayDefinitionCommandService;
    @Autowired
    RedisTemplate redisTemplate;
    private String imei="865328063321359";

    @Test
    void findByImei() {

        List<RelayDefinitionCommand> relayDefinitionCommands = relayDefinitionCommandService.findByImei(imei);
        Assert.isTrue(relayDefinitionCommands.size()==3,"测试其元素为3个");
        Object o = redisTemplate.opsForValue().get(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY + "::" + imei);
        Assert.notNull( o,"缓存中有值！");
    }

    @Test
    void findById() {
        RelayDefinitionCommand relayDefinitionCommand = relayDefinitionCommandService.findById(1L);
        Assert.isNull(relayDefinitionCommand,"relayDefinitionCommand不为空值");
    }

    @Test
    void save() {
        relayDefinitionCommandService.save(new RelayDefinitionCommand(imei,"打开大棚","打开大棚","1,2,3",true,1L,1L,1L));
        Object o = redisTemplate.opsForValue().get(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY + "::" + imei);
        Assert.isNull( o,"缓存中应该无值！");
        List<RelayDefinitionCommand> relayDefinitionCommands = relayDefinitionCommandService.findByImei(imei);
        Assert.isTrue(relayDefinitionCommands.size()==4,"测试其元素为4个");
        Object o1 = redisTemplate.opsForValue().get(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY + "::" + imei);
        Assert.notNull( o1,"缓存中有值！");
    }

    @Test
    void update() {
        RelayDefinitionCommand relayDefinitionCommand = relayDefinitionCommandService.findById(1L);
        relayDefinitionCommand.setRelayIds("2");
        RelayDefinitionCommand update = relayDefinitionCommandService.update(relayDefinitionCommand);
        Assert.isTrue(update.getRelayIds().equals("2"),"修改值成功");
        Object o = redisTemplate.opsForValue().get(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY + "::" + imei);
        Assert.isNull( o,"缓存中应该无值！");
        List<RelayDefinitionCommand> relayDefinitionCommands = relayDefinitionCommandService.findByImei(imei);
        Assert.isTrue(relayDefinitionCommands.size()==3,"测试其元素为3个");
        Object o1 = redisTemplate.opsForValue().get(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY + "::" + imei);
        Assert.notNull( o1,"缓存中有值！");
    }

    @Test
    void delete() {
        relayDefinitionCommandService.delete(1L);
        Object o = redisTemplate.opsForValue().get(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY + "::" + imei);
        Assert.isNull( o,"缓存中应该无值！");
        List<RelayDefinitionCommand> relayDefinitionCommands = relayDefinitionCommandService.findByImei(imei);
        Assert.isTrue(relayDefinitionCommands.size()==2,"测试其元素为2个");
        Object o1 = redisTemplate.opsForValue().get(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY + "::" + imei);
        Assert.notNull( o1,"缓存中有值！");
    }

    @Test
    void deleteAllByImei() {
        relayDefinitionCommandService.deleteAllByImei(imei);
        Object o = redisTemplate.opsForValue().get(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY + "::" + imei);
        Assert.isNull( o,"缓存中应该无值！");
        List<RelayDefinitionCommand> relayDefinitionCommands = relayDefinitionCommandService.findByImei(imei);
        Assert.isNull(relayDefinitionCommands,"应该为空值");
        Object o1 = redisTemplate.opsForValue().get(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY + "::" + imei);
        Assert.isNull( o1,"缓存中应该无值！");
    }
}