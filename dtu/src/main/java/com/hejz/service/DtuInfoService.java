package com.hejz.service;

import com.hejz.dto.DtuInfoFindByPageDto;
import com.hejz.entity.DtuInfo;
import org.springframework.data.domain.Page;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 08:35
 * @Description:
 */
public interface DtuInfoService {
    DtuInfo findById(Long id);

    DtuInfo save(DtuInfo dtuInfo);

    DtuInfo update(DtuInfo dtuInfo);

    void delete(Long id);

    DtuInfo findByImei(String Imei);

    Page<DtuInfo> findPage(DtuInfoFindByPageDto dto);
}
