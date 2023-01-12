package com.hejz.studay.service;

import com.hejz.studay.entity.Sensor;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 07:48
 * @Description:
 */
public interface SensorService {
    List<Sensor> getSensorByImei(String imei);

    Sensor getSensorById(Long id);

    Sensor save(Sensor sensor);

    Sensor update(Sensor sensor);

    void delete(Long id);

    void deleteByImei(String imei);
}
