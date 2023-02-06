//package com.hejz.service.impl;
//
//import com.hejz.common.Constant;
//import com.hejz.enm.InstructionTypeEnum;
//import com.hejz.entity.InstructionDefinition;
//import com.hejz.service.RelayDefinitionCommandService;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.util.Assert;
//
//import java.util.List;
//
//@SpringBootTest
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class InstructionDefinitionServiceImplTest {
//
//    @Autowired
//    RelayDefinitionCommandService relayDefinitionCommandService;
//    @Autowired
//    RedisTemplate redisTemplate;
//    private Long dtuId = 1L;
//
//    @Order(5)
//    @Test
//    void findByImei() {
//        List<InstructionDefinition> instructionDefinitions = relayDefinitionCommandService.findByAllDtuId(dtuId);
//        Assert.isTrue(instructionDefinitions.size() == 3, "测试其元素为3个");
//        Object o = redisTemplate.opsForValue().get(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY + "::" + dtuId);
//        Assert.notNull(o, "缓存中有值！");
//    }
//
//    @Order(3)
//    @Test
//    void findById() {
//        InstructionDefinition instructionDefinition = relayDefinitionCommandService.findById(1L);
//        Assert.notNull(instructionDefinition, "relayDefinitionCommand不为空值");
//    }
//
//    @Order(1)
//    @Test
//    void save() {
//        relayDefinitionCommandService.save(new InstructionDefinition(dtuId, "打开大棚", "打开大棚", "1,2,3",  1L, 1L, 1L, InstructionTypeEnum.OPEN_VENTILATION));
//        Object o = redisTemplate.opsForValue().get(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY + "::" + dtuId);
//        Assert.isNull(o, "缓存中应该无值！");
//        List<InstructionDefinition> instructionDefinitions = relayDefinitionCommandService.findByAllDtuId(dtuId);
//        Assert.isTrue(instructionDefinitions.size() == 4, "测试其元素为4个");
//        Object o1 = redisTemplate.opsForValue().get(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY + "::" + dtuId);
//        Assert.notNull(o1, "缓存中有值！");
//    }
//
//    @Order(2)
//    @Test
//    void update() {
//        InstructionDefinition instructionDefinition = relayDefinitionCommandService.findById(1L);
//        instructionDefinition.setRelayIds("2");
//        InstructionDefinition update = relayDefinitionCommandService.update(instructionDefinition);
//        Assert.isTrue(update.getRelayIds().equals("2"), "修改值成功");
//        Object o = redisTemplate.opsForValue().get(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY + "::" + dtuId);
//        Assert.isNull(o, "缓存中应该无值！");
//        List<InstructionDefinition> instructionDefinitions = relayDefinitionCommandService.findByAllDtuId(dtuId);
//        Assert.isTrue(instructionDefinitions.size() == 4, "测试其元素为4个");
//        Object o1 = redisTemplate.opsForValue().get(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY + "::" + dtuId);
//        Assert.notNull(o1, "缓存中有值！");
//    }
//
//    @Order(4)
//    @Test
//    void delete() {
//        relayDefinitionCommandService.delete(1L);
//        Object o = redisTemplate.opsForValue().get(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY + "::" + dtuId);
//        Assert.isNull(o, "缓存中应该无值！");
//        List<InstructionDefinition> instructionDefinitions = relayDefinitionCommandService.findByAllDtuId(dtuId);
//        Assert.isTrue(instructionDefinitions.size() == 3, "测试其元素为3个");
//        Object o1 = redisTemplate.opsForValue().get(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY + "::" + dtuId);
//        Assert.notNull(o1, "缓存中有值！");
//    }
//
//    @Order(6)
//    @Test
//    void deleteAllByImei() {
//        relayDefinitionCommandService.deleteAllByDtuId(dtuId);
//        Object o = redisTemplate.opsForValue().get(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY + "::" + dtuId);
//        Assert.isNull(o, "缓存中应该无值！");
//        List<InstructionDefinition> instructionDefinitions = relayDefinitionCommandService.findByAllDtuId(dtuId);
//        Assert.isTrue(instructionDefinitions.isEmpty(), "应该为空值");
//        Object o1 = redisTemplate.opsForValue().get(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY + "::" + dtuId);
//        Assert.isTrue(instructionDefinitions.isEmpty(), "应该为空值");
//    }
//}