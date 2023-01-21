package com.hejz.service.impl;

import com.hejz.entity.SensorDataDb;
import com.hejz.repository.SensorDataDbRepository;
import com.hejz.service.SensorDataDbService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private SensorDataDbRepository selayRepository;

    @Override
    public List<SensorDataDb> findAllByDtuId(Long dtuId) {
        return selayRepository.findAllByDtuId(dtuId);
    }

    @Override
    public SensorDataDb getById(Long id) {
        SensorDataDb selay = selayRepository.findById(id);
        return selay;
    }

    @Override
    public SensorDataDb save(SensorDataDb selay) {
        return selayRepository.save(selay);
    }

    @Override
    public SensorDataDb update(SensorDataDb selay) {
        return selayRepository.save(selay);
    }

    @Override
    public void delete(Long id) {
        selayRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllByDtuId(Long dutId) {
        selayRepository.deleteAllByDtuId(dutId);
    }

}
