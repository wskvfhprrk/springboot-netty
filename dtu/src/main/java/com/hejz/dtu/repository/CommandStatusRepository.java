package com.hejz.dtu.repository;

import com.hejz.dtu.entity.CommandStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 继电器命令状态 dao层
 * author: hejz
 * data: 2023-2-7
 */
public interface CommandStatusRepository extends JpaRepository<CommandStatus,Long>,JpaSpecificationExecutor<CommandStatus> {
}
