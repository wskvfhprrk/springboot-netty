package com.hejz.service.impl;

import com.hejz.common.Constant;
import com.hejz.entity.DtuInfo;
import com.hejz.entity.Relay;
import com.hejz.repository.RelayRepository;
import com.hejz.service.DtuInfoService;
import com.hejz.service.RelayService;
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
public class RelayServiceImpl implements RelayService {
    @Autowired
    private RelayRepository selayRepository;
    @Autowired
    private DtuInfoService dtuInfoService;
    @Autowired
    RedisTemplate redisTemplate;

    @Cacheable(value = Constant.RELAY_CACHE_KEY, key = "#p0", unless = "#result == null")
    public List<Relay> findAllByDtuId(Long dtuId) {
        return selayRepository.findAlByDtuId(dtuId);
    }

    @Override
    public Relay findById(Long id) {
        Relay selay = selayRepository.findById(id).orElse(null);
        return selay;
    }

    @CacheEvict(value = Constant.RELAY_CACHE_KEY, key = "#result.dtuId")
    @Override
    public Relay save(Relay selay) {
        selay.setCloseHex(null);
        return selayRepository.save(selay);
    }

    @CacheEvict(value = Constant.RELAY_CACHE_KEY, key = "#result.dtuId")
    @Override
    public Relay update(Relay selay) {
        return selayRepository.save(selay);
    }

    @Override
    public void delete(Long id) {
        //缓存同步
        Relay relay = selayRepository.findById(id).orElse(null);
        DtuInfo dtuInfo = dtuInfoService.findById(relay.getDtuId());
        redisTemplate.delete(Constant.RELAY_CACHE_KEY + "::" + dtuInfo.getId());
        selayRepository.deleteById(id);
    }

    @CacheEvict(value = Constant.RELAY_CACHE_KEY, key = "#p0")
    @Override
    @Transactional
    public void deleteAlByDtuId(Long dtuId) {
        selayRepository.deleteAllByDtuId(dtuId);
    }

}
