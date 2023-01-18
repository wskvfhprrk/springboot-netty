package com.hejz.service;

import com.hejz.entity.DtuInfo;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 08:35
 * @Description:
 */
public interface DtuInfoService {
    List<DtuInfo> findAllByImei(String imei);

    DtuInfo findById(Long id);

    DtuInfo save(DtuInfo dtuInfo);

    DtuInfo update(DtuInfo dtuInfo);

    void delete(Long id);

    void deleteAllByImei(String imei);
}
