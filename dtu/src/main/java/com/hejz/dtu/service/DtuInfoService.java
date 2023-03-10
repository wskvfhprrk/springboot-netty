package com.hejz.dtu.service;

import com.hejz.dtu.common.Result;
import com.hejz.dtu.dto.DtuInfoFindAllDto;
import com.hejz.dtu.dto.DtuInfoFindByPageDto;
import com.hejz.dtu.entity.DtuInfo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *
 */
public interface DtuInfoService {
    DtuInfo save(DtuInfo dtuInfo);
    DtuInfo update(DtuInfo dtuInfo);
    Result delete(Long id);
    DtuInfo findById(Long id);
    Page<DtuInfo> findPage(DtuInfoFindByPageDto dto);

    DtuInfo findByImei(String imei);

    List<DtuInfo> findAll(DtuInfoFindAllDto dto);
}
