package com.hejz.service;


import com.hejz.common.Page;
import com.hejz.common.PageResult;
import com.hejz.common.Result;
import com.hejz.entity.SensorDataDb;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 08:35
 * @Description:
 */
public interface SensorDataDbService {
    List<SensorDataDb> findAllByDtuId(Long dtuId);

    SensorDataDb getById(Long id);

    SensorDataDb save(SensorDataDb dtuInfo);

    SensorDataDb update(SensorDataDb dtuInfo);

    void delete(Long id);

    void deleteAllByDtuId(Long dtuId);

    Result<PageResult> getPage(Page page);
}
