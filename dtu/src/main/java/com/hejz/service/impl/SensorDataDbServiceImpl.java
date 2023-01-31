package com.hejz.service.impl;

import com.hejz.common.Page;
import com.hejz.common.PageResult;
import com.hejz.common.Result;
import com.hejz.entity.SensorDataDb;
import com.hejz.repository.SensorDataDbRepository;
import com.hejz.service.SensorDataDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Result<PageResult> getPage(Page page) {

//        List<SensorDataDb> sensorDataDbs = sensorDataDbRepository.findAll(Sort.by("create_date").descending());
        return null;
    }

}
