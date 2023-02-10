package com.hejz.dtu.service;

import com.hejz.dtu.dto.SensorDataFindByPageDto;
import com.hejz.dtu.entity.SensorData;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *
 */
public interface SensorDataService {
    SensorData save(SensorData sensorData);
    void delete(Long id);
    SensorData findById(Long id);
    Page<SensorData> findPage(SensorDataFindByPageDto dto);

}
