package com.hejz.dtu.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hejz.dtu.common.Constant;
import com.hejz.dtu.dto.SensorFindByPageDto;
import com.hejz.dtu.entity.DtuInfo;
import com.hejz.dtu.entity.Sensor;
import com.hejz.dtu.repository.SensorRepository;
import com.hejz.dtu.service.SensorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SensorServiceImpl implements SensorService {

    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Sensor save(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    @CacheEvict(value = Constant.SENSOR_CACHE_KEY, key = "#p0.id")
    @Override
    public Sensor update(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    @CacheEvict(value = Constant.SENSOR_CACHE_KEY, key = "#p0")
    @Override
    public void delete(Long id) {
        sensorRepository.deleteById(id);
    }

    @Override
    public Sensor findById(Long id) {
       return sensorRepository.findById( id).orElse(null);
    }

    @Override
    @Transactional
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
        //???????????????????????????-???????????????+?????????,?????????????????????
        Sort.Direction direction = dto.getSort().substring(0, 1).equals("+") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, dto.getSort().substring(1));
        Page<Sensor> all = sensorRepository.findAll(sp, PageRequest.of(dto.getPage(), dto.getLimit(), sort));
        System.out.println(all);
        return all;
    }


    @Override
    public List<Sensor> findAllByDtuInfo(DtuInfo dtuInfo) {
        Object o = redisTemplate.opsForValue().get(Constant.SENSOR_CACHE_KEY + "::" + dtuInfo.getId());
        List<Sensor> sensors=new ArrayList<>();
        try {
            if(o!=null){
                sensors = objectMapper.readValue(o.toString(), new TypeReference<List<Sensor>>() {
                });
            }else {
                sensors = sensorRepository.findAllByDtuInfo(dtuInfo);
                String value = objectMapper.writeValueAsString(sensors);
                redisTemplate.opsForValue().set(Constant.SENSOR_CACHE_KEY + "::" + dtuInfo.getId(),value);
            }
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return sensors;
    }
}
