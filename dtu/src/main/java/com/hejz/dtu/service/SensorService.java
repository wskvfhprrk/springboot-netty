package com.hejz.dtu.service;

import com.hejz.dtu.dto.SensorFindByPageDto;
import com.hejz.dtu.entity.DtuInfo;
import com.hejz.dtu.entity.Sensor;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *
 */
public interface SensorService {
    Sensor save(Sensor sensor);
    Sensor update(Sensor sensor);
    void delete(Long id);
    Sensor findById(Long id);
    Page<Sensor> findPage(SensorFindByPageDto dto);
    List<Sensor> findAllByDtuInfo(DtuInfo dtuInfo);
}
