package com.hejz.dtu.service.impl;

import com.hejz.dtu.common.Constant;
import com.hejz.dtu.dto.DtuInfoFindAllDto;
import com.hejz.dtu.dto.DtuInfoFindByPageDto;
import com.hejz.dtu.entity.DtuInfo;
import com.hejz.dtu.entity.User;
import com.hejz.dtu.repository.DtuInfoRepository;
import com.hejz.dtu.service.DtuInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DtuInfoServiceImpl implements DtuInfoService {

    @Autowired
    private DtuInfoRepository dtuInfoRepository;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public DtuInfo save(DtuInfo dtuInfo) {
        return dtuInfoRepository.save(dtuInfo);
    }
    @CacheEvict(value = Constant.DTU_INFO_CACHE_KEY, key = "#p0.id")
    @Override
    public DtuInfo update(DtuInfo dtuInfo) {
        redisTemplate.delete(Constant.DTU_INFO_IMEI_CACHE_KEY+"::"+dtuInfo.getImei());
        return dtuInfoRepository.save(dtuInfo);
    }
    @CacheEvict(value = Constant.DTU_INFO_CACHE_KEY, key = "#p0")
    @Override
    public void delete(Long id) {
        Optional<DtuInfo> dtuInfo = dtuInfoRepository.findById(id);
        redisTemplate.delete(Constant.DTU_INFO_IMEI_CACHE_KEY+"::"+dtuInfo.get().getImei());
        dtuInfoRepository.deleteById( id);
    }

    @Cacheable(value = Constant.DTU_INFO_CACHE_KEY, key = "#p0",unless = "#result==null")
    @Override
    public DtuInfo findById(Long id) {
       return dtuInfoRepository.findById( id).orElse(null);
    }

    @Override
    public Page<DtuInfo> findPage(DtuInfoFindByPageDto dto) {
        Specification<DtuInfo> sp= (root, query, cb)-> {
            List<Predicate> predicates = new ArrayList<>();
            Join<DtuInfo, User> join= root.join("user", JoinType.LEFT);
            if(dto.getAutomaticAdjustment()!=null) {
            predicates.add(cb.equal(root.get("automaticAdjustment"), dto.getAutomaticAdjustment()));
            }
            if(StringUtils.isNotBlank(dto.getImei())) {
                predicates.add(cb.like(root.get("imei"), "%"+dto.getImei()+"%"));
            }
            if(dto.getIntervalTime()!=null && dto.getIntervalTime()!=0) {
            predicates.add(cb.equal(root.get("intervalTime"), dto.getIntervalTime()));
            }
            if(dto.getRegistrationLength()!=null && dto.getRegistrationLength()!=0) {
            predicates.add(cb.equal(root.get("registrationLength"), dto.getRegistrationLength()));
            }
            if(StringUtils.isNotBlank(dto.getSensorAddressOrder())) {
                predicates.add(cb.like(root.get("sensorAddressOrder"), "%"+dto.getSensorAddressOrder()+"%"));
            }
            if(dto.getUserId()!=null && dto.getUserId()!=0) {
            predicates.add(cb.equal(join.get("id"), dto.getUserId()));
            }
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        //截取第一个字符，为-是倒序，为+正排序,后面为字段名称
        Sort.Direction direction = dto.getSort().substring(0, 1).equals("+") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, dto.getSort().substring(1));
        Page<DtuInfo> all = dtuInfoRepository.findAll(sp, PageRequest.of(dto.getPage(), dto.getLimit(), sort));
        return all;
    }
    @Cacheable(value = Constant.DTU_INFO_IMEI_CACHE_KEY,key = "#p0",unless = "#result==null")
    @Override
    public DtuInfo findByImei(String imei) {
        return dtuInfoRepository.findByImei(imei);
    }

    @Override
    public List<DtuInfo> findAll(DtuInfoFindAllDto dto) {
        Specification<DtuInfo> sp= (root, query, cb)-> {
            List<Predicate> predicates = new ArrayList<>();
            if(dto.getAutomaticAdjustment()!=null) {
                predicates.add(cb.equal(root.get("automaticAdjustment"), dto.getAutomaticAdjustment()));
            }
            if(StringUtils.isNotBlank(dto.getImei())) {
                predicates.add(cb.like(root.get("imei"), "%"+dto.getImei()+"%"));
            }
            if(dto.getIntervalTime()!=null && dto.getIntervalTime()!=0) {
                predicates.add(cb.equal(root.get("intervalTime"), dto.getIntervalTime()));
            }
            if(dto.getRegistrationLength()!=null && dto.getRegistrationLength()!=0) {
                predicates.add(cb.equal(root.get("registrationLength"), dto.getRegistrationLength()));
            }
            if(StringUtils.isNotBlank(dto.getSensorAddressOrder())) {
                predicates.add(cb.like(root.get("sensorAddressOrder"), "%"+dto.getSensorAddressOrder()+"%"));
            }
            if(dto.getUserId()!=null && dto.getUserId()!=0) {
                predicates.add(cb.equal(root.get("userId"), dto.getUserId()));
            }
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        //截取第一个字符，为-是倒序，为+正排序,后面为字段名称
        List<DtuInfo> all = dtuInfoRepository.findAll(sp);
        return all;
    }
}
