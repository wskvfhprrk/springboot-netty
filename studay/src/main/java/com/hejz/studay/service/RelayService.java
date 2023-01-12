package com.hejz.studay.service;

import com.hejz.studay.entity.Relay;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 08:35
 * @Description:
 */
public interface RelayService {
    List<Relay> getByImei(String imei);

    Relay getById(Long id);

    Relay save(Relay relay);

    Relay update(Relay relay);

    void delete(Long id);

    void deleteByImei(String imei);
}
