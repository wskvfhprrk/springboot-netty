package com.hejz.dtu.repository;

import com.hejz.dtu.entity.Command;
import com.hejz.dtu.entity.DtuInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 指令 dao层
 * author: hejz
 * data: 2023-2-7
 */
public interface CommandRepository extends JpaRepository<Command,Long>,JpaSpecificationExecutor<Command> {
}
