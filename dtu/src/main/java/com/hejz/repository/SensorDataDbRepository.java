package com.hejz.repository;

import com.hejz.entity.SensorDataDb;
import com.hejz.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * 角色 dao层
 * author: hejz
 * data: 2022-5-9
 */
public interface SensorDataDbRepository extends CrudRepository<SensorDataDb, Date>, JpaSpecificationExecutor<SensorDataDb> {
    List<SensorDataDb> findAllByDtuId(Long dtuId);

    SensorDataDb findById(Long id);

    void deleteById(Long id);

    void deleteAllByDtuId(Long dutId);

}
