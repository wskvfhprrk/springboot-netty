package com.hejz.studay.service.impl;

import com.hejz.studay.entity.DtuInfo;
import com.hejz.studay.entity.Relay;
import com.hejz.studay.entity.RelayDefinitionCommand;
import com.hejz.studay.entity.Sensor;
import com.hejz.studay.repository.DtuInfoRepository;
import com.hejz.studay.repository.RelayDefinitionCommandRepository;
import com.hejz.studay.repository.RelayRepository;
import com.hejz.studay.repository.SensorRepository;
import com.hejz.studay.service.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-11 21:22
 * @Description: redis缓存类
 */
@Service
public class RedisCacheCacheServiceImpl implements RedisCacheService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    DtuInfoRepository dtuInfoRepository;
    @Autowired
    RelayRepository relayRepository;
    @Autowired
    RelayDefinitionCommandRepository relayDefinitionCommandRepository;
    @Autowired
    SensorRepository sensorRepository;

    @Cacheable(value = "dtuInfo", key = "#imei")
    @Override
    public DtuInfo getDtuInfoByImei(String imei) {
        return dtuInfoRepository.getDtuInfoByImei(imei);
    }
    @Cacheable(value = "relay", key = "#imei")
    @Override
    public List<Relay> getRelayByImei(String imei) {
        return relayRepository.getAllByImei(imei);
    }
    @Cacheable(value = "relayDefinitionCommand", key = "#imei")
    @Override
    public List<RelayDefinitionCommand> getRelayDefinitionCommandByImei(String imei) {
        return relayDefinitionCommandRepository.getAllByImei(imei);
    }
    @Cacheable(value = "sensor", key = "#imei")
    @Override
    public List<Sensor> getSensorByImei(String imei) {
        return sensorRepository.getAllByImei(imei);
    }
}
