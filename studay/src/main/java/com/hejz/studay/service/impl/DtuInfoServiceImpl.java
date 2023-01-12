package com.hejz.studay.service.impl;

import com.hejz.studay.common.Constant;
import com.hejz.studay.entity.DtuInfo;
import com.hejz.studay.repository.DtuInfoRepository;
import com.hejz.studay.service.DtuInfoService;
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

    @Cacheable(value = Constant.DTU_INFO, key = "#p0")
    @Override
    public DtuInfo getByImei(String imei) {
        return dtuInfoRepository.getAllByImei(imei);
    }

    @Override
    public DtuInfo getById(Long id) {
        DtuInfo dtuInfo = dtuInfoRepository.getById(id);
        return dtuInfo;
    }

    @CacheEvict(value = Constant.DTU_INFO, key = "#result.imei")
    @Override
    public DtuInfo save(DtuInfo dtuInfo) {
        return dtuInfoRepository.save(dtuInfo);
    }

    @CacheEvict(value = Constant.DTU_INFO, key = "#result.imei")
    @Override
    public DtuInfo update(DtuInfo dtuInfo) {
        return dtuInfoRepository.save(dtuInfo);
    }

    @Override
    public void delete(Long id) {
        DtuInfo dtuInfo = dtuInfoRepository.getById(id);
        redisTemplate.delete(Constant.DTU_INFO+"::"+dtuInfo.getImei());
        dtuInfoRepository.deleteById(id);
    }

    @CacheEvict(value = Constant.DTU_INFO, key = "#p0")
    @Override
    public void deleteByImei(String imei) {
        dtuInfoRepository.deleteByImei(imei);
    }

}
