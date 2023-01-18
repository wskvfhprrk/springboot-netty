package com.hejz.service.impl;

import com.hejz.common.Constant;
import com.hejz.entity.Sensor;
import com.hejz.repository.SensorRepository;
import com.hejz.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 07:55
 * @Description:
 */
@Service
public class SensorServiceImpl implements SensorService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    private SensorRepository sensorRepository;

    @Cacheable(value = Constant.SENSOR_CACHE_KEY, key = "#p0", unless = "#result == null")
    @Override
    public List<Sensor> findByImei(String imei) {
        return sensorRepository.getAllByImei(imei);
    }

    @Override
    public Sensor findById(Long id) {
        Sensor sensor = sensorRepository.findById(id).orElse(null);
        return sensor;
    }

    @CacheEvict(value = Constant.SENSOR_CACHE_KEY, key = "#result.imei")
    @Override
    public Sensor save(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    @CacheEvict(value = Constant.SENSOR_CACHE_KEY, key = "#result.imei")
    @Override
    public Sensor update(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    @Override
    public void delete(Long id) {
        Sensor sensor = sensorRepository.findById(id).orElse(null);
        redisTemplate.delete(Constant.SENSOR_CACHE_KEY + "::" + sensor.getImei());
        sensorRepository.deleteById(id);
    }

    @CacheEvict(value = Constant.SENSOR_CACHE_KEY, key = "#p0")
    @Override
    public void deleteAllByImei(String imei) {
        sensorRepository.deleteAllByImei(imei);
    }

}
