package com.hejz.studay.service;

import com.hejz.studay.entity.DtuInfo;
import com.hejz.studay.entity.Relay;
import com.hejz.studay.entity.RelayDefinitionCommand;
import com.hejz.studay.entity.Sensor;

import java.util.List;

/**
 * redis缓存方法类
 */
public interface RedisCacheService {
    DtuInfo getDtuInfoByImei(String imei);
    List<Relay> getRelayByImei(String imei);
    List<RelayDefinitionCommand> getRelayDefinitionCommandByImei(String imei);
    List<Sensor> getSensorByImei(String imie);
}
