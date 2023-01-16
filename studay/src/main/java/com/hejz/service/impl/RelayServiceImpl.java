package com.hejz.service.impl;

import com.hejz.common.Constant;
import com.hejz.entity.Relay;
import com.hejz.repository.RelayRepository;
import com.hejz.service.RelayService;
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
public class RelayServiceImpl implements RelayService {
    @Autowired
    private RelayRepository selayRepository;
    @Autowired
    RedisTemplate redisTemplate;

    @Cacheable(value = Constant.RELAY_CACHE_KEY, key = "#p0", unless = "#result == null")
    @Override
    public List<Relay> getByImei(String imei) {
        return selayRepository.getAllByImei(imei);
    }

    @Override
    public Relay getById(Long id) {
        Relay selay = selayRepository.getById(id);
        return selay;
    }

    @CacheEvict(value = Constant.RELAY_CACHE_KEY, key = "#result.imei")
    @Override
    public Relay save(Relay selay) {
        selay.setCloseHex(null);
        return selayRepository.save(selay);
    }

    @CacheEvict(value = Constant.RELAY_CACHE_KEY, key = "#result.imei")
    @Override
    public Relay update(Relay selay) {
        return selayRepository.save(selay);
    }

    @Override
    public void delete(Long id) {
        //缓存同步
        Relay relay = selayRepository.getById(id);
        redisTemplate.delete(Constant.RELAY_CACHE_KEY + "::" + relay.getImei());
        selayRepository.deleteById(id);
    }

    @CacheEvict(value = Constant.RELAY_CACHE_KEY, key = "#p0")
    @Override
    public void deleteByImei(String imei) {
        selayRepository.deleteByImei(imei);
    }

}
