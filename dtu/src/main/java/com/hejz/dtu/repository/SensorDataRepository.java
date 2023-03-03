package com.hejz.dtu.repository;

import com.hejz.dtu.entity.SensorData;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 传感器数据 dao层
 * author: hejz
 * data: 2023-2-7
 */
public interface SensorDataRepository extends JpaRepository<SensorData,Long>,JpaSpecificationExecutor<SensorData> {
}
