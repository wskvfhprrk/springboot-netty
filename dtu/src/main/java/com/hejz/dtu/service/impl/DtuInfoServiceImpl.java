package com.hejz.dtu.service.impl;

import com.hejz.dtu.dto.DtuInfoFindByPageDto;
import com.hejz.dtu.entity.DtuInfo;
import com.hejz.dtu.repository.DtuInfoRepository;
import com.hejz.dtu.service.DtuInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DtuInfoServiceImpl implements DtuInfoService {

    @Autowired
    private DtuInfoRepository dtuInfoRepository;

    @Override
    public DtuInfo Save(DtuInfo dtuInfo) {
        return dtuInfoRepository.save(dtuInfo);
    }

    @Override
    public void delete(Long id) {
        dtuInfoRepository.deleteById( id);
    }

    @Override
    public DtuInfo findById(Long id) {
       return dtuInfoRepository.findById( id).orElse(null);
    }

    @Override
    public Page<DtuInfo> findPage(DtuInfoFindByPageDto dto) {
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
        Sort.Direction direction = dto.getSort().substring(0, 1).equals("+") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, dto.getSort().substring(1));
        Page<DtuInfo> all = dtuInfoRepository.findAll(sp, PageRequest.of(dto.getPage(), dto.getLimit(), sort));
        System.out.println(all);
        return all;
    }

}
