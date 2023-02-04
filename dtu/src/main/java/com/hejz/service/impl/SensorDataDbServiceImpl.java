package com.hejz.service.impl;

import com.hejz.dto.SensorDataDbFindByPageDto;
import com.hejz.entity.SensorDataDb;
import com.hejz.repository.SensorDataDbRepository;
import com.hejz.service.SensorDataDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 07:55
 * @Description:
 */
@Service
public class SensorDataDbServiceImpl implements SensorDataDbService {
    @Autowired
    private SensorDataDbRepository sensorDataDbRepository;

    @Override
    public List<SensorDataDb> findAllByDtuId(Long dtuId) {
        return sensorDataDbRepository.findAllByDtuId(dtuId);
    }

    @Override
    public SensorDataDb getById(Long id) {
        SensorDataDb selay = sensorDataDbRepository.findById(id);
        return selay;
    }

    @Override
    public SensorDataDb save(SensorDataDb selay) {
        return sensorDataDbRepository.save(selay);
    }

    @Override
    public SensorDataDb update(SensorDataDb selay) {
        return sensorDataDbRepository.save(selay);
    }

    @Override
    public void delete(Long id) {
        sensorDataDbRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllByDtuId(Long dutId) {
        sensorDataDbRepository.deleteAllByDtuId(dutId);
    }


    @Override
    public Page<SensorDataDb> findPage(SensorDataDbFindByPageDto dto) {
        Specification<SensorDataDb> sp = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (dto.getDtuId() != null && dto.getDtuId() != 0) {
                predicates.add(cb.equal(root.get("dtuId"), dto.getDtuId()));
            }
            if (dto.getBeginDate() != null && dto.getEndDate() != null) {
                predicates.add(cb.between(root.get("createDate"), dto.getBeginDate(), dto.getEndDate()));
            }
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        //截取第一个字符，为-是倒序，为+正排序
        Sort.Direction direction = dto.getSort().substring(0, 1).equals("+") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, dto.getSort().substring(1));
        Page<SensorDataDb> all = sensorDataDbRepository.findAll(sp, PageRequest.of(dto.getPage(), dto.getLimit(), sort));
        System.out.println(all);
        return all;
    }

    public static void main(String[] args) {
        String a="123456789";
        System.out.println(a.substring(1));
    }


}
