package com.hejz.studay.service.impl;

import com.hejz.studay.entity.Sensor;
import com.hejz.studay.repository.SensorRepository;
import com.hejz.studay.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 07:55
 * @Description:
 */
@Service
public class SensorServiceImpl implements SensorService {
    @Autowired
    private SensorRepository sensorRepository;
    @Override
    public List<Sensor> getByImei(String imei) {
        return sensorRepository.getAllByImei(imei);
    }

    @Override
    public Sensor getById(Long id) {
        Sensor sensor = sensorRepository.getById(id);
        return sensor;
    }

    @Override
    public Sensor save(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    @Override
    public Sensor update(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    @Override
    public void delete(Long id) {
         sensorRepository.deleteById(id);
    }

    @Override
    public void deleteByImei(String imei) {
        sensorRepository.deleteByImei(imei);
    }

}
