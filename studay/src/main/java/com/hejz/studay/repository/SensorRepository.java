package com.hejz.studay.repository;

import com.hejz.studay.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 用户信息 dao层
 * author: hejz
 * data: 2022-5-9
 */
public interface SensorRepository extends JpaRepository<Sensor,Long>,JpaSpecificationExecutor<Sensor> {
    List<Sensor> getAllByImei(String imei);

    void deleteById(Long id);

    void deleteByImei(String imei);
}
