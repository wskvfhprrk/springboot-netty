package com.hejz.dtu.repository;

import com.hejz.dtu.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;

/**
 * dtu信息 dao层
 * author: hejz
 * data: 2023-2-7
 */
public interface WeatherRepository extends JpaRepository<Weather,Long>,JpaSpecificationExecutor<Weather> {
    Weather findByCreateTime(Date createTime);
}
