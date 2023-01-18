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

    @Cacheable(value = Constant.DTU_INFO_CACHE_KEY, key = "#p0",unless="#result == null")
    @Override
    public DtuInfo findByImei(String imei) {
        return dtuInfoRepository.getAllByImei(imei);
    }

    @Override
    public DtuInfo findById(Long id) {
        return dtuInfoRepository.findById(id).orElse(null);
    }

    @CacheEvict(value = Constant.DTU_INFO_CACHE_KEY, key = "#result.imei")
    @Override
    public DtuInfo save(DtuInfo dtuInfo) {
        return dtuInfoRepository.save(dtuInfo);
    }

    @CacheEvict(value = Constant.DTU_INFO_CACHE_KEY, key = "#result.imei")
    @Override
    public DtuInfo update(DtuInfo dtuInfo) {
        return dtuInfoRepository.save(dtuInfo);
    }

    @Override
    public void delete(Long id) {
        DtuInfo dtuInfo = dtuInfoRepository.findById(id).orElse(null);
        redisTemplate.delete(Constant.DTU_INFO_CACHE_KEY+"::"+dtuInfo.getImei());
        dtuInfoRepository.deleteById(id);
    }

    @CacheEvict(value = Constant.DTU_INFO_CACHE_KEY, key = "#p0")
    @Override
    public void deleteByImei(String imei) {
        dtuInfoRepository.deleteByImei(imei);
    }

}
