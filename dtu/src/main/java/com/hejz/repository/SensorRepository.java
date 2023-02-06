package com.hejz.repository;

import com.hejz.entity.DtuInfo;
import com.hejz.entity.Sensor;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * 用户信息 dao层
 * author: hejz
 * data: 2022-5-9
 */
public interface SensorRepository extends CrudRepository<Sensor,Long>,JpaSpecificationExecutor<Sensor> {

    List<Sensor> findAllByDtuInfo(DtuInfo dtuInfo);
}
