package com.hejz.studay.repository;

import com.hejz.studay.entity.SensorDataDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;

/**
 * 角色 dao层
 * author: hejz
 * data: 2022-5-9
 */
public interface SensorDataDbRepository extends JpaRepository<SensorDataDb, Date>,JpaSpecificationExecutor<SensorDataDb> {
}
