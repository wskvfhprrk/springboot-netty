package com.hejz.service;

import com.hejz.common.Result;
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

    /**
     * 手动模式关闭大棚
     * @param dtuId
     * @return
     */
    Result closeTheCanopyInManualMode(Long dtuId);

    /**
     * 手动模式开启大棚
     * @param dtuId
     * @return
     */
    Result openTheCanopyInManualMode(Long dtuId);

    /**
     * 切换大棚自动调整模式
     * @param dtuId
     * @return
     */
    Result changeAutomaticAdjustment(Long dtuId);
}
