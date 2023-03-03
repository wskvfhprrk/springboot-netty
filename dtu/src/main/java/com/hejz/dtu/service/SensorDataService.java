package com.hejz.dtu.service;

import com.hejz.dtu.common.Result;
import com.hejz.dtu.dto.GetChartDataDto;
import com.hejz.dtu.dto.SensorDataFindByPageDto;
import com.hejz.dtu.entity.SensorData;
import com.hejz.dtu.vo.EchartsVo;
import org.springframework.data.domain.Page;

/**
 *
 */
public interface SensorDataService {
    SensorData save(SensorData sensorData);
    void delete(Long id);
    SensorData findById(Long id);
    Page<SensorData> findPage(SensorDataFindByPageDto dto);
    Result<EchartsVo> getChartData(GetChartDataDto i);

    Result<SensorData> getLast(Long dtuId);
}
