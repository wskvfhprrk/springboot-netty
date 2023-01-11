package com.hejz.studay.repository;

import com.hejz.studay.entity.Relay;
import com.hejz.studay.entity.RelayDefinitionCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 用户信息 dao层
 * author: hejz
 * data: 2022-5-9
 */
public interface RelayDefinitionCommandRepository extends JpaRepository<RelayDefinitionCommand,Long>,JpaSpecificationExecutor<RelayDefinitionCommand> {
    List<RelayDefinitionCommand> getAllByImei(String imei);
}
