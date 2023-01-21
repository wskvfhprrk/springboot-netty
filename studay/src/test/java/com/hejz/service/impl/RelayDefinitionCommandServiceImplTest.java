package com.hejz.service.impl;

import com.hejz.common.Constant;
import com.hejz.entity.RelayDefinitionCommand;
import com.hejz.service.RelayDefinitionCommandService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RelayDefinitionCommandServiceImplTest {

    @Autowired
    RelayDefinitionCommandService relayDefinitionCommandService;
    @Autowired
    RedisTemplate redisTemplate;
    private Long dtuId = 1L;

    @Order(5)
    @Test
    void findByImei() {
        List<RelayDefinitionCommand> relayDefinitionCommands = relayDefinitionCommandService.findByAllDtuId(dtuId);
        Assert.isTrue(relayDefinitionCommands.size() == 3, "测试其元素为3个");
        Object o = redisTemplate.opsForValue().get(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY + "::" + dtuId);
        Assert.notNull(o, "缓存中有值！");
    }

    @Order(3)
    @Test
    void findById() {
        RelayDefinitionCommand relayDefinitionCommand = relayDefinitionCommandService.findById(1L);
        Assert.notNull(relayDefinitionCommand, "relayDefinitionCommand不为空值");
    }

    @Order(1)
    @Test
    void save() {
        relayDefinitionCommandService.save(new RelayDefinitionCommand(dtuId, "打开大棚", "打开大棚", "1,2,3", true, 1L, 1L, 1L));
        Object o = redisTemplate.opsForValue().get(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY + "::" + dtuId);
        Assert.isNull(o, "缓存中应该无值！");
        List<RelayDefinitionCommand> relayDefinitionCommands = relayDefinitionCommandService.findByAllDtuId(dtuId);
        Assert.isTrue(relayDefinitionCommands.size() == 4, "测试其元素为4个");
        Object o1 = redisTemplate.opsForValue().get(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY + "::" + dtuId);
        Assert.notNull(o1, "缓存中有值！");
    }

    @Order(2)
    @Test
    void update() {
        RelayDefinitionCommand relayDefinitionCommand = relayDefinitionCommandService.findById(1L);
        relayDefinitionCommand.setRelayIds("2");
        RelayDefinitionCommand update = relayDefinitionCommandService.update(relayDefinitionCommand);
        Assert.isTrue(update.getRelayIds().equals("2"), "修改值成功");
        Object o = redisTemplate.opsForValue().get(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY + "::" + dtuId);
        Assert.isNull(o, "缓存中应该无值！");
        List<RelayDefinitionCommand> relayDefinitionCommands = relayDefinitionCommandService.findByAllDtuId(dtuId);
        Assert.isTrue(relayDefinitionCommands.size() == 4, "测试其元素为4个");
        Object o1 = redisTemplate.opsForValue().get(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY + "::" + dtuId);
        Assert.notNull(o1, "缓存中有值！");
    }

    @Order(4)
    @Test
    void delete() {
        relayDefinitionCommandService.delete(1L);
        Object o = redisTemplate.opsForValue().get(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY + "::" + dtuId);
        Assert.isNull(o, "缓存中应该无值！");
        List<RelayDefinitionCommand> relayDefinitionCommands = relayDefinitionCommandService.findByAllDtuId(dtuId);
        Assert.isTrue(relayDefinitionCommands.size() == 3, "测试其元素为3个");
        Object o1 = redisTemplate.opsForValue().get(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY + "::" + dtuId);
        Assert.notNull(o1, "缓存中有值！");
    }

    @Order(6)
    @Test
    void deleteAllByImei() {
        relayDefinitionCommandService.deleteAllByDtuId(dtuId);
        Object o = redisTemplate.opsForValue().get(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY + "::" + dtuId);
        Assert.isNull(o, "缓存中应该无值！");
        List<RelayDefinitionCommand> relayDefinitionCommands = relayDefinitionCommandService.findByAllDtuId(dtuId);
        Assert.isTrue(relayDefinitionCommands.isEmpty(), "应该为空值");
        Object o1 = redisTemplate.opsForValue().get(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY + "::" + dtuId);
        Assert.isTrue(relayDefinitionCommands.isEmpty(), "应该为空值");
    }
}