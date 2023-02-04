package com.hejz.service;

import com.hejz.dto.SensorFindByPageDto;
import com.hejz.entity.Sensor;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 07:48
 * @Description:
 */
public interface SensorService {
    List<Sensor> findAllByDtuId(Long dtuId);

    Sensor findById(Long id);

    Sensor save(Sensor sensor);

    Sensor update(Sensor sensor);

    void delete(Long id);

    void deleteAllByDtuId(Long dtuId);

    Page<Sensor> findPage(SensorFindByPageDto dto);
}
