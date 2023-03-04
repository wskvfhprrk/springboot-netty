package com.hejz.dtu.service;

import com.hejz.dtu.dto.*;
import com.hejz.dtu.entity.Weather;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *
 */
public interface WeatherService {

    Weather save(Weather weather);

    Weather update(Weather weather);

    void delete(Long id);

    Weather findById(Long id);

    Page<Weather> findPage(WeatherFindByPageDto dto);

    List<Weather> findAll(WeatherAllDto dto);
}
