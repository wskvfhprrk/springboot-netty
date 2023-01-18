package com.hejz.service;

import com.hejz.entity.Sensor;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 07:48
 * @Description:
 */
public interface SensorService {
    List<Sensor> findByImei(String imei);

    Sensor findById(Long id);

    Sensor save(Sensor sensor);

    Sensor update(Sensor sensor);

    void delete(Long id);

    void deleteAllByImei(String imei);
}
