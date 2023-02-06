package com.hejz.service.impl;

import com.hejz.common.Constant;
import com.hejz.dto.SensorFindByPageDto;
import com.hejz.entity.DtuInfo;
import com.hejz.entity.Sensor;
import com.hejz.repository.SensorRepository;
import com.hejz.service.DtuInfoService;
import com.hejz.service.SensorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 07:55
 * @Description:
 */
@Service
public class SensorServiceImpl implements SensorService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private DtuInfoService dtuInfoService;

    @Cacheable(value = Constant.SENSOR_CACHE_KEY, key = "#p0", unless = "#result == null")
    @Override
    public List<Sensor> findAllByDtuId(Long dtuId) {
        DtuInfo dtuInfo = dtuInfoService.findById(dtuId);
        return sensorRepository.findAllByDtuInfo(dtuInfo);
    }

    @Override
    public Sensor findById(Long id) {
        Sensor sensor = sensorRepository.findById(id).orElse(null);
        return sensor;
    }

    @CacheEvict(value = Constant.SENSOR_CACHE_KEY, key = "#sensor.dtuId")
    @Override
    public Sensor save(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    @CacheEvict(value = Constant.SENSOR_CACHE_KEY, key = "#sensor.dtuId")
    @Override
    public Sensor update(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    @Override
    public void delete(Long id) {
        Sensor sensor = sensorRepository.findById(id).orElse(null);
        redisTemplate.delete(Constant.SENSOR_CACHE_KEY + "::" + sensor.getDtuInfo().getId());
        sensorRepository.deleteById(id);
    }

    @CacheEvict(value = Constant.SENSOR_CACHE_KEY, key = "#p0")
    @Override
    @Transactional
    public void deleteAllByDtuId(Long dtuId) {
        DtuInfo dtuInfo = dtuInfoService.findById(dtuId);
        List<Sensor> sensors = sensorRepository.findAllByDtuInfo(dtuInfo);
        sensors.forEach(sensor -> sensorRepository.deleteById(sensor.getId()));
    }

    @Override
    public Page<Sensor> findPage(SensorFindByPageDto dto) {
        Specification<Sensor> sp= (root, query, cb)-> {
            List<Predicate> predicates = new ArrayList<>();
            if (dto.getAdrss() != null && dto.getAdrss() != 0) {
                predicates.add(cb.equal(root.get("adrss"), dto.getAdrss()));
            }
            if (StringUtils.isNotBlank(dto.getCalculationFormula())) {
                predicates.add(cb.like(root.get("calculationFormula"), "%" + dto.getCalculationFormula() + "%"));
            }
            if (dto.getDtuId() != null && dto.getDtuId() != 0) {
                predicates.add(cb.equal(root.get("dtuId"), dto.getDtuId()));
            }
            if (StringUtils.isNotBlank(dto.getHex())) {
                predicates.add(cb.like(root.get("hex"), "%" + dto.getHex() + "%"));
            }
            if (dto.getMax() != null && dto.getMax() != 0) {
                predicates.add(cb.equal(root.get("max"), dto.getMax()));
            }
            if (dto.getMaxRelayDefinitionCommandId() != null && dto.getMaxRelayDefinitionCommandId() != 0) {
                predicates.add(cb.equal(root.get("maxRelayDefinitionCommandId"), dto.getMaxRelayDefinitionCommandId()));
            }
            if (dto.getMin() != null && dto.getMin() != 0) {
                predicates.add(cb.equal(root.get("min"), dto.getMin()));
            }
            if (dto.getMinRelayDefinitionCommandId() != null && dto.getMinRelayDefinitionCommandId() != 0) {
                predicates.add(cb.equal(root.get("minRelayDefinitionCommandId"), dto.getMinRelayDefinitionCommandId()));
            }
            if (StringUtils.isNotBlank(dto.getName())) {
                predicates.add(cb.like(root.get("name"), "%" + dto.getName() + "%"));
            }
            if (StringUtils.isNotBlank(dto.getUnit())) {
                predicates.add(cb.like(root.get("unit"), "%" + dto.getUnit() + "%"));
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
