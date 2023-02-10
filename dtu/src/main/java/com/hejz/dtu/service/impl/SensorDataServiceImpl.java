package com.hejz.dtu.service.impl;

import com.hejz.dtu.dto.SensorDataFindByPageDto;
import com.hejz.dtu.entity.SensorData;
import com.hejz.dtu.repository.SensorDataRepository;
import com.hejz.dtu.service.SensorDataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SensorDataServiceImpl implements SensorDataService {

    @Autowired
    private SensorDataRepository sensorDataRepository;

    @Override
    public SensorData save(SensorData sensorData) {
        return sensorDataRepository.save(sensorData);
    }


    @Override
    public void delete(Long id) {
        sensorDataRepository.deleteById( id);
    }

    @Override
    public SensorData findById(Long id) {
       return sensorDataRepository.findById( id).orElse(null);
    }

    @Override
    public Page<SensorData> findPage(SensorDataFindByPageDto dto) {
        Specification<SensorData> sp= (root, query, cb)-> {
            List<Predicate> predicates = new ArrayList<>();
            if(dto.getCreateDate()!=null) {
            predicates.add(cb.equal(root.get("createDate"), dto.getCreateDate()));
            }
            if(StringUtils.isNotBlank(dto.getData())) {
                predicates.add(cb.like(root.get("data"), "%"+dto.getData()+"%"));
            }
            if(StringUtils.isNotBlank(dto.getNames())) {
                predicates.add(cb.like(root.get("names"), "%"+dto.getNames()+"%"));
            }
            if(StringUtils.isNotBlank(dto.getUnits())) {
                predicates.add(cb.like(root.get("units"), "%"+dto.getUnits()+"%"));
            }
            if(dto.getDtuId()!=null && dto.getDtuId()!=0) {
            predicates.add(cb.equal(root.get("dtuId"), dto.getDtuId()));
            }
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        //截取第一个字符，为-是倒序，为+正排序,后面为字段名称
        Sort.Direction direction = dto.getSort().substring(0, 1).equals("+") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, dto.getSort().substring(1));
        Page<SensorData> all = sensorDataRepository.findAll(sp, PageRequest.of(dto.getPage(), dto.getLimit(), sort));
        System.out.println(all);
        return all;
    }

}
