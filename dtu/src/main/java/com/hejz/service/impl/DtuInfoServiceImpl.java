package com.hejz.service.impl;

import com.hejz.common.Constant;
import com.hejz.common.Result;
import com.hejz.dto.DtuInfoFindByPageDto;
import com.hejz.entity.DtuInfo;
import com.hejz.repository.DtuInfoRepository;
import com.hejz.service.DtuInfoService;
import com.hejz.service.RelayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private RelayService relayService;
    @Autowired
    RedisTemplate redisTemplate;

    @Cacheable(value = Constant.DTU_INFO_IMEI_CACHE_KEY, key = "#p0", unless = "#result == null")
    @Override
    public DtuInfo findByImei(String imei) {
        return dtuInfoRepository.findAllByImei(imei);
    }

    @Override
    public Page<DtuInfo> findPage(DtuInfoFindByPageDto dto) {
        Specification<DtuInfo> sp = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (dto.getImei() != null && dto.getImei().length()>0) {
                predicates.add(cb.equal(root.get("imei"), dto.getImei()));
            }
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        //截取第一个字符，为-是倒序，为+正排序,后面为字段名称
        Sort.Direction direction = dto.getSort().substring(0, 1).equals("+") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, dto.getSort().substring(1));
        Page<DtuInfo> all = dtuInfoRepository.findAll(sp, PageRequest.of(dto.getPage(), dto.getLimit(), sort));
        System.out.println(all);
        return all;
    }

    @Override
    public Result closeTheCanopyInManualMode(Long dtuId) {
        //先变手动模式，然后再发命令
        Optional<DtuInfo> optionalDtuInfo = dtuInfoRepository.findById(dtuId);
        if(optionalDtuInfo.isPresent()){
            DtuInfo dtuInfo = optionalDtuInfo.get();
            dtuInfo.setAutomaticAdjustment(false);
            this.update(dtuInfo);
            relayService.closeTheCanopy(dtuId);
        }
        return Result.ok();
    }

    @Override
    public Result openTheCanopyInManualMode(Long dtuId) {
        //先变手动模式，然后再发命令
        Optional<DtuInfo> optionalDtuInfo = dtuInfoRepository.findById(dtuId);
        if(optionalDtuInfo.isPresent()){
            DtuInfo dtuInfo = optionalDtuInfo.get();
            dtuInfo.setAutomaticAdjustment(false);
            this.update(dtuInfo);
            relayService.openTheCanopy(dtuId);
        }
        return Result.ok();
    }

    @Override
    public Result changeAutomaticAdjustment(Long dtuId) {
        Optional<DtuInfo> optionalDtuInfo = dtuInfoRepository.findById(dtuId);
        if(optionalDtuInfo.isPresent()){
            Boolean aBoolean = optionalDtuInfo.get().getAutomaticAdjustment();
            optionalDtuInfo.get().setAutomaticAdjustment(aBoolean?false:true);
            this.update(optionalDtuInfo.get());
        }
        return Result.ok();
    }

    @Cacheable(value = Constant.DTU_INFO_CACHE_KEY, key = "#p0", unless = "#result == null")
    @Override
    public DtuInfo findById(Long id) {
        return dtuInfoRepository.findById(id).orElse(null);
    }

    @CacheEvict(value = Constant.DTU_INFO_CACHE_KEY, key = "#result.id")
    @Override
    public DtuInfo save(DtuInfo dtuInfo) {
        redisTemplate.delete(Constant.DTU_INFO_IMEI_CACHE_KEY+"::"+dtuInfo.getImei());
        return dtuInfoRepository.save(dtuInfo);
    }

    @CacheEvict(value = Constant.DTU_INFO_CACHE_KEY, key = "#po.id")
    @Override
    public DtuInfo update(DtuInfo dtuInfo) {
        redisTemplate.delete(Constant.DTU_INFO_IMEI_CACHE_KEY+"::"+dtuInfo.getImei());
        return dtuInfoRepository.save(dtuInfo);
    }

    @Override
    public void delete(Long id) {
        DtuInfo dtuInfo = dtuInfoRepository.findById(id).orElse(null);
        redisTemplate.delete(Constant.DTU_INFO_IMEI_CACHE_KEY+"::"+dtuInfo.getImei());
        redisTemplate.delete(Constant.DTU_INFO_CACHE_KEY + "::" + dtuInfo.getId());
        dtuInfoRepository.deleteById(id);
    }
}
