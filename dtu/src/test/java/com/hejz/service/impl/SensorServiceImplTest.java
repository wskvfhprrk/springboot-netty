//package com.hejz.service.impl;
//
//import com.hejz.common.Constant;
//import com.hejz.entity.Sensor;
//import com.hejz.service.SensorService;
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
//class SensorServiceImplTest {
//
//    @Autowired
//    SensorService sensorService;
//    @Autowired
//    RedisTemplate redisTemplate;
//    private Long dtuId = 1L;
//
//    @Order(1)
//    @Test
//    void findByImei() {
//        List<Sensor> sensors = sensorService.findAllByDtuId(dtuId);
//        Assert.isTrue(sensors.size() == 9, "测试其元素为9个");
//        Object o = redisTemplate.opsForValue().get(Constant.SENSOR_CACHE_KEY + "::" + dtuId);
//        Assert.isTrue(((ArrayList) o).size()==9, "测试其元素为9个");
//    }
//
//    @Order(3)
//    @Test
//    void findById() {
//        Sensor sensor = sensorService.findById(1L);
//        Assert.notNull(sensor, "sensor不为空值");
//    }
//
//    @Order(2)
//    @Test
//    void save() {
////        sensorService.save(new Sensor(dtuId, 1,"打开大棚", "打开大棚", "1,2,9", "1",1,1,1L,1L));
////        Object o = redisTemplate.opsForValue().get(Constant.SENSOR_CACHE_KEY + "::" + dtuId);
////        Assert.isNull(o, "缓存中应该无值！");
////        List<Sensor> sensors = sensorService.findAllByDtuId(dtuId);
////        Assert.isTrue(sensors.size() == 10, "测试其元素为10个");
////        Object o1 = redisTemplate.opsForValue().get(Constant.SENSOR_CACHE_KEY + "::" + dtuId);
////        Assert.isTrue(((ArrayList) o1).size()==10, "测试其元素为10个");
//    }
//
//    @Order(4)
//    @Test
//    void update() {
////        Sensor sensor = sensorService.findById(1L);
////        sensor.setAdress(1);
////        Sensor update = sensorService.update(sensor);
////        Assert.isTrue(update.getAdress()==1, "修改值成功");
////        Object o = redisTemplate.opsForValue().get(Constant.SENSOR_CACHE_KEY + "::" + dtuId);
////        Assert.isNull(o, "缓存中应该无值！");
////        List<Sensor> sensors = sensorService.findAllByDtuId(dtuId);
////        Assert.isTrue(sensors.size() == 10, "测试其元素为10个");
////        Object o1 = redisTemplate.opsForValue().get(Constant.SENSOR_CACHE_KEY + "::" + dtuId);
////        Assert.isTrue(((ArrayList) o1).size()==10, "测试其元素为10个");
//    }
//
//    @Order(5)
//    @Test
//    void delete() {
////        sensorService.delete(1L);
////        Object o = redisTemplate.opsForValue().get(Constant.SENSOR_CACHE_KEY + "::" + dtuId);
////        Assert.isNull(o, "缓存中应该无值！");
////        List<Sensor> sensors = sensorService.findAllByDtuId(dtuId);
////        Assert.isTrue(sensors.size() == 9, "测试其元素为9个");
////        Object o1 = redisTemplate.opsForValue().get(Constant.SENSOR_CACHE_KEY + "::" + dtuId);
////        Assert.isTrue(((ArrayList) o1).size()==9, "测试其元素为9个");
////    }
//
//    @Order(6)
//    @Test
//    void deleteAllByImei() {
////        sensorService.deleteAllByDtuId(dtuId);
////        Object o = redisTemplate.opsForValue().get(Constant.SENSOR_CACHE_KEY + "::" + dtuId);
////        Assert.isNull(o, "缓存中应该无值！");
////        List<Sensor> sensors = sensorService.findAllByDtuId(dtuId);
////        Assert.isTrue(sensors.isEmpty(), "应该为空值");
////        Object o1 = redisTemplate.opsForValue().get(Constant.SENSOR_CACHE_KEY + "::" + dtuId);
////        Assert.isTrue(((ArrayList) o1).size()==0, "测试其元素为0个");
//    }
//}