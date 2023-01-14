package com.hejz.service;


import com.hejz.entity.CommandStatus;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 08:35
 * @Description:
 */
public interface CommandStatusService {
    List<CommandStatus> getByImei(String imei);

    CommandStatus getById(Long id);

    CommandStatus save(CommandStatus commandStatus);

    CommandStatus update(CommandStatus commandStatus);

    void delete(Long id);

    void deleteByImei(String imei);
}