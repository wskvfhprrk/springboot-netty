package com.hejz.service;

import com.hejz.common.Result;
import com.hejz.dto.DtuInfoDto;
import com.hejz.dto.DtuInfoFindByPageDto;
import com.hejz.dto.DtuInfoUpdateDto;
import com.hejz.entity.DtuInfo;
import org.springframework.data.domain.Page;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 08:35
 * @Description:
 */
public interface DtuInfoService {
    DtuInfo findById(Long id);

    DtuInfo save(DtuInfoDto dtuInfo);

    DtuInfo update(DtuInfoUpdateDto dtuInfo);

    void delete(Long id);

    DtuInfo findByImei(String Imei);

    Page<DtuInfo> findPage(DtuInfoFindByPageDto dto);
    /**
     * 切换大棚自动调整模式
     * @param dtuId
     * @return
     */
    Result changeAutomaticAdjustment(Long dtuId);
}
