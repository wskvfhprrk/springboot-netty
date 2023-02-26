package com.hejz.dtu.repository;

import com.hejz.dtu.entity.Command;
import com.hejz.dtu.entity.DtuInfo;
import com.hejz.dtu.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;

/**
 * 指令 dao层
 * author: hejz
 * data: 2023-2-7
 */
public interface WeatherRepository extends JpaRepository<Weather,Long>,JpaSpecificationExecutor<Weather> {

    Weather findByCreateTime(Date createdTime);
}
