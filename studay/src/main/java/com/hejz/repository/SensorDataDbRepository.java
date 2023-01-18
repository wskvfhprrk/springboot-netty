package com.hejz.repository;

import com.hejz.entity.SensorDataDb;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * 角色 dao层
 * author: hejz
 * data: 2022-5-9
 */
public interface SensorDataDbRepository extends CrudRepository<SensorDataDb, Date>,JpaSpecificationExecutor<SensorDataDb> {
    List<SensorDataDb> getAllByImei(String imei);

    void deleteAllByImei(String imei);

    SensorDataDb findById(Long id);

    void deleteById(Long id);
}
