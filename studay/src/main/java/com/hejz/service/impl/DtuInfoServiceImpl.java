package com.hejz.service.impl;

import com.hejz.common.Constant;
import com.hejz.entity.DtuInfo;
import com.hejz.repository.DtuInfoRepository;
import com.hejz.service.DtuInfoService;
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
public class DtuInfoServiceImpl implements DtuInfoService {
    @Autowired
    private DtuInfoRepository dtuInfoRepository;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public DtuInfo findAllByImei(String imei) {
        return dtuInfoRepository.findAllByImei(imei);
    }
    @Cacheable(value = Constant.DTU_INFO_CACHE_KEY, key = "#p0",unless="#result == null")
    @Override
    public DtuInfo findById(Long id) {
        return dtuInfoRepository.findById(id).orElse(null);
    }

    @CacheEvict(value = Constant.DTU_INFO_CACHE_KEY, key = "#result.id")
    @Override
    public DtuInfo save(DtuInfo dtuInfo) {
        return dtuInfoRepository.save(dtuInfo);
    }

    @CacheEvict(value = Constant.DTU_INFO_CACHE_KEY, key = "#po.id")
    @Override
    public DtuInfo update(DtuInfo dtuInfo) {
        return dtuInfoRepository.save(dtuInfo);
    }

    @Override
    public void delete(Long id) {
        DtuInfo dtuInfo = dtuInfoRepository.findById(id).orElse(null);
        redisTemplate.delete(Constant.DTU_INFO_CACHE_KEY+"::"+dtuInfo.getId());
        dtuInfoRepository.deleteById(id);
    }
}
