package com.hejz.dtu.repository;

import com.hejz.dtu.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 传感器 dao层
 * author: hejz
 * data: 2023-2-7
 */
public interface SensorRepository extends JpaRepository<Sensor,Long>,JpaSpecificationExecutor<Sensor> {
}
