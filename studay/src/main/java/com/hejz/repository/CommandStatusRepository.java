package com.hejz.repository;

import com.hejz.entity.CommandStatus;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.Arrays;
import java.util.List;

/**
 * 用户信息 dao层
 * author: hejz
 * data: 2022-5-9
 */
public interface CommandStatusRepository extends CrudRepository<CommandStatus,Long>,JpaSpecificationExecutor<CommandStatus> {


    List<CommandStatus> findAllByImei(String imei);

}
