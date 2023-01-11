package com.hejz.studay.service.impl;

import com.hejz.studay.entity.DtuInfo;
import com.hejz.studay.repository.DtuInfoRepository;
import com.hejz.studay.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-11 21:22
 * @Description: redis缓存类
 */
public class RedisServiceImpl implements RedisService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    DtuInfoRepository dtuInfoRepository;

    @Cacheable(value = "dtuInfo", key = "'imei'")
    @Override
    public DtuInfo findDtuInfoByImei(String imei) {
        return dtuInfoRepository.getDtuInfoByImei(imei);
    }
}
