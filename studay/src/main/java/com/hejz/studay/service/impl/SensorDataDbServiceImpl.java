package com.hejz.studay.service.impl;

import com.hejz.studay.entity.SensorDataDb;
import com.hejz.studay.repository.SensorDataDbRepository;
import com.hejz.studay.service.SensorDataDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<SensorDataDb> getSensorDataDbByImei(String imei) {
        return selayRepository.getAllByImei(imei);
    }
    @Override
    public SensorDataDb getSensorDataDbById(Long id) {
        SensorDataDb selay = selayRepository.getById(id);
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
    public void deleteByImei(String imei) {
        selayRepository.deleteByImei(imei);
    }

}