package com.hejz.service.impl;

import com.hejz.common.Constant;
import com.hejz.entity.DtuInfo;
import com.hejz.entity.Sensor;
import com.hejz.repository.SensorRepository;
import com.hejz.service.DtuInfoService;
import com.hejz.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private DtuInfoService dtuInfoService;

    @Cacheable(value = Constant.SENSOR_CACHE_KEY, key = "#p0", unless = "#result == null")
    @Override
    public List<Sensor> findAllByDtuId(Long dtuId) {
        return sensorRepository.findAllByDtuId(dtuId);
    }

    @Override
    public Sensor findById(Long id) {
        Sensor sensor = sensorRepository.findById(id).orElse(null);
        return sensor;
    }

    @CacheEvict(value = Constant.SENSOR_CACHE_KEY, key = "#sensor.dtuId")
    @Override
    public Sensor save(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    @CacheEvict(value = Constant.SENSOR_CACHE_KEY, key = "#sensor.dtuId")
    @Override
    public Sensor update(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    @Override
    public void delete(Long id) {
        Sensor sensor = sensorRepository.findById(id).orElse(null);
        DtuInfo dtuInfo = dtuInfoService.findById(sensor.getDtuId());
        redisTemplate.delete(Constant.SENSOR_CACHE_KEY + "::" + sensor.getDtuId());
        sensorRepository.deleteById(id);
    }

    @CacheEvict(value = Constant.SENSOR_CACHE_KEY, key = "#p0")
    @Override
    @Transactional
    public void deleteAllByDtuId(Long dtuId) {
        sensorRepository.deleteAllByDtuId(dtuId);
    }

}
