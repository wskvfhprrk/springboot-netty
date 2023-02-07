package com.hejz.dtu.service.impl;

import com.hejz.dtu.dto.SensorFindByPageDto;
import com.hejz.dtu.entity.Sensor;
import com.hejz.dtu.repository.SensorRepository;
import com.hejz.dtu.service.SensorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SensorServiceImpl implements SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    @Override
    public Sensor Save(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    @Override
    public void delete(Long id) {
        sensorRepository.deleteById( id);
    }

    @Override
    public Sensor findById(Long id) {
       return sensorRepository.findById( id).orElse(null);
    }

    @Override
    public Page<Sensor> findPage(SensorFindByPageDto dto) {
        Specification<Sensor> sp= (root, query, cb)-> {
            List<Predicate> predicates = new ArrayList<>();
            if(dto.getMax()!=null && dto.getMax()!=0) {
            predicates.add(cb.equal(root.get("max"), dto.getMax()));
            }
            if(dto.getMin()!=null && dto.getMin()!=0) {
            predicates.add(cb.equal(root.get("min"), dto.getMin()));
            }
            if(StringUtils.isNotBlank(dto.getName())) {
                predicates.add(cb.like(root.get("name"), "%"+dto.getName()+"%"));
            }
            if(dto.getDtuId()!=null && dto.getDtuId()!=0) {
            predicates.add(cb.equal(root.get("dtuId"), dto.getDtuId()));
            }
            if(dto.getMaxInstructionDefinitionId()!=null && dto.getMaxInstructionDefinitionId()!=0) {
            predicates.add(cb.equal(root.get("maxInstructionDefinitionId"), dto.getMaxInstructionDefinitionId()));
            }
            if(dto.getMinInstructionDefinitionId()!=null && dto.getMinInstructionDefinitionId()!=0) {
            predicates.add(cb.equal(root.get("minInstructionDefinitionId"), dto.getMinInstructionDefinitionId()));
            }
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        //截取第一个字符，为-是倒序，为+正排序,后面为字段名称
        Sort.Direction direction = dto.getSort().substring(0, 1).equals("+") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, dto.getSort().substring(1));
        Page<Sensor> all = sensorRepository.findAll(sp, PageRequest.of(dto.getPage(), dto.getLimit(), sort));
        System.out.println(all);
        return all;
    }

}
