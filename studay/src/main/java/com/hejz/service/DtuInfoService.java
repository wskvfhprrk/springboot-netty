package com.hejz.service;

import com.hejz.entity.DtuInfo;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 08:35
 * @Description:
 */
public interface DtuInfoService {
    DtuInfo getByImei(String imei);

    DtuInfo getById(Long id);

    DtuInfo save(DtuInfo dtuInfo);

    DtuInfo update(DtuInfo dtuInfo);

    void delete(Long id);

    void deleteByImei(String imei);
}
