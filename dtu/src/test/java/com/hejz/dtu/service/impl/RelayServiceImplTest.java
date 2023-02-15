//package com.hejz.service.impl;
//
//import com.hejz.common.Constant;
//import com.hejz.entity.Relay;
//import com.hejz.service.RelayService;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.util.Assert;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@SpringBootTest
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class RelayServiceImplTest {
//
//    @Autowired
//    RelayService relayService;
//    @Autowired
//    RedisTemplate redisTemplate;
//    private Long dtuId = 1L;
//
//    @Order(5)
//    @Test
//    void findByImei() {
//        List<Relay> relays = relayService.findAllByDtuId(dtuId);
//        Assert.isTrue(relays.size() == 4, "测试其元素为4个");
//        Object o = redisTemplate.opsForValue().get(Constant.RELAY_CACHE_KEY + "::" + dtuId);
//        Assert.isTrue(((ArrayList) o).size()==4, "测试其元素为4个");
//    }
//
//    @Order(4)
//    @Test
//    void findById() {
//        Relay relay = relayService.findById(1L);
//        Assert.notNull(relay, "relay不为空值");
//    }
//
//    @Order(1)
//    @Test
//    void save() {
//        relayService.save(new Relay(dtuId, 1,"打开大棚", "打开大棚", "1,2,4", "1","2"));
//        Object o = redisTemplate.opsForValue().get(Constant.RELAY_CACHE_KEY + "::" + dtuId);
//        Assert.isNull(o, "缓存中应该无值！");
//        List<Relay> relays = relayService.findAllByDtuId(dtuId);
//        Assert.isTrue(relays.size() == 5, "测试其元素为5个");
//        Object o1 = redisTemplate.opsForValue().get(Constant.RELAY_CACHE_KEY + "::" + dtuId);
//        Assert.isTrue(((ArrayList) o1).size()==5, "测试其元素为5个");
//    }
//
//    @Order(2)
//    @Test
//    void update() {
//        Relay relay = relayService.findById(1L);
//        relay.setAdrss(1);
//        Relay update = relayService.update(relay);
//        Assert.isTrue(update.getAdrss()==1, "修改值成功");
//        Object o = redisTemplate.opsForValue().get(Constant.RELAY_CACHE_KEY + "::" + dtuId);
//        Assert.isNull(o, "缓存中应该无值！");
//        List<Relay> relays = relayService.findAllByDtuId(dtuId);
//        Assert.isTrue(relays.size() == 5, "测试其元素为5个");
//        Object o1 = redisTemplate.opsForValue().get(Constant.RELAY_CACHE_KEY + "::" + dtuId);
//        Assert.isTrue(((ArrayList) o1).size()==5, "测试其元素为5个");
//    }
//
//    @Order(5)
//    @Test
//    void delete() {
//        relayService.delete(1L);
//        Object o = redisTemplate.opsForValue().get(Constant.RELAY_CACHE_KEY + "::" + dtuId);
//        Assert.isNull(o, "缓存中应该无值！");
//        List<Relay> relays = relayService.findAllByDtuId(dtuId);
//        Assert.isTrue(relays.size() == 4, "测试其元素为4个");
//        Object o1 = redisTemplate.opsForValue().get(Constant.RELAY_CACHE_KEY + "::" + dtuId);
//        Assert.isTrue(((ArrayList) o1).size()==4, "测试其元素为4个");
//    }
//
//    @Order(6)
//    @Test
//    void deleteAllByImei() {
//        relayService.deleteAlByDtuId(dtuId);
//        Object o = redisTemplate.opsForValue().get(Constant.RELAY_CACHE_KEY + "::" + dtuId);
//        Assert.isNull(o, "缓存中应该无值！");
//        List<Relay> relays = relayService.findAllByDtuId(dtuId);
//        Assert.isTrue(relays.isEmpty(), "应该为空值");
//        Object o1 = redisTemplate.opsForValue().get(Constant.RELAY_CACHE_KEY + "::" + dtuId);
//        Assert.isTrue(((ArrayList) o1).size()==0, "测试其元素为0个");
//    }
//}