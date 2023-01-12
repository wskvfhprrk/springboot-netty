package com.hejz.studay.service;


import com.hejz.studay.entity.RelayDefinitionCommand;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 08:35
 * @Description:
 */
public interface RelayDefinitionCommandService {
    List<RelayDefinitionCommand> getByImei(String imei);

    RelayDefinitionCommand getById(Long id);

    RelayDefinitionCommand save(RelayDefinitionCommand dtuInfo);

    RelayDefinitionCommand update(RelayDefinitionCommand dtuInfo);

    void delete(Long id);

    void deleteByImei(String imei);
}
