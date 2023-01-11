package com.hejz.studay.service;

import com.hejz.studay.entity.DtuInfo;

/**
 * redis缓存方法类
 */
public interface RedisService {
    DtuInfo findDtuInfoByImei(String imei);
}
