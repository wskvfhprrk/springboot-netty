package com.hejz.service.impl;

import com.hejz.common.Constant;
import com.hejz.entity.DtuInfo;
import com.hejz.repository.DtuInfoRepository;
import com.hejz.service.DtuInfoService;
import org.springframework.beans.BeanUtils;
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
    public DtuInfo getByImei(String imei) {
        return dtuInfoRepository.getAllByImei(imei);
    }

    @Cacheable(value = Constant.DUT_INFO_ID_CACHE_KEY, key = "#p0",unless="#result == null")
    @Override
    public DtuInfo getById(Long id) {
        DtuInfo dtuInfo = dtuInfoRepository.getById(id);
        DtuInfo d=new DtuInfo();
        BeanUtils.copyProperties(dtuInfo,d);
        return d;
    }

    @CacheEvict(value = Constant.DTU_INFO_CACHE_KEY, key = "#result.imei")
    @Override
    public DtuInfo save(DtuInfo dtuInfo) {
        return dtuInfoRepository.save(dtuInfo);
    }

    @CacheEvict(value = Constant.DTU_INFO_CACHE_KEY, key = "#result.imei")
    @Override
    public DtuInfo update(DtuInfo dtuInfo) {
        redisTemplate.delete(Constant.DUT_INFO_ID_CACHE_KEY+"::"+dtuInfo.getId());
        return dtuInfoRepository.save(dtuInfo);
    }

    @Override
    public void delete(Long id) {
        DtuInfo dtuInfo = dtuInfoRepository.getById(id);
        redisTemplate.delete(Constant.DTU_INFO_CACHE_KEY + "::" + dtuInfo.getImei());
        dtuInfoRepository.deleteById(id);
    }

    @CacheEvict(value = Constant.DTU_INFO_CACHE_KEY, key = "#p0")
    @Override
    public void deleteByImei(String imei) {
        DtuInfo dtuInfo = dtuInfoRepository.getAllByImei(imei);
        redisTemplate.delete(Constant.DUT_INFO_ID_CACHE_KEY+"::"+dtuInfo.getId());
        dtuInfoRepository.deleteByImei(imei);
    }

}
