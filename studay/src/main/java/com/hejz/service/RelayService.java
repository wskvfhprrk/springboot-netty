package com.hejz.service;

import com.hejz.entity.Relay;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 08:35
 * @Description:
 */
public interface RelayService {
    List<Relay> findAllByDtuId(Long dtuId);

    Relay findById(Long id);

    Relay save(Relay relay);

    Relay update(Relay relay);

    void delete(Long id);

    void deleteAlByDtuId(Long dtuId);
}
