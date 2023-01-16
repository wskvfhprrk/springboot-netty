package com.hejz.repository;

import com.hejz.entity.CommandStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 用户信息 dao层
 * author: hejz
 * data: 2022-5-9
 */
public interface CommandStatusRepository extends JpaRepository<CommandStatus,Long>,JpaSpecificationExecutor<CommandStatus> {
    List<CommandStatus> getByImei(String imei);

    void deleteByImeiAndStatus(String imei,Boolean b);
}
