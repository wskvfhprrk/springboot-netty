package com.hejz.dtu.service;

import com.hejz.dtu.dto.DtuInfoFindByPageDto;
import com.hejz.dtu.entity.DtuInfo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *
 */
public interface DtuInfoService {
    DtuInfo Save(DtuInfo dtuInfo);
    void delete(Long id);
    DtuInfo findById(Long id);
    Page<DtuInfo> findPage(DtuInfoFindByPageDto dto);
}
