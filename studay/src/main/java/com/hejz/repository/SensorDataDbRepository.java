package com.hejz.repository;

import com.hejz.entity.SensorDataDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

/**
 * 角色 dao层
 * author: hejz
 * data: 2022-5-9
 */
public interface SensorDataDbRepository extends JpaRepository<SensorDataDb, Date>,JpaSpecificationExecutor<SensorDataDb> {
    List<SensorDataDb> getAllByImei(String imei);

    void deleteByImei(String imei);

    SensorDataDb findById(Long id);

    void deleteById(Long id);
}
