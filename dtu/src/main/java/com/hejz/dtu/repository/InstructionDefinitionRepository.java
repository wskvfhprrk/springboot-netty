package com.hejz.dtu.repository;

import com.hejz.dtu.entity.InstructionDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 继电器定义指令 dao层
 * author: hejz
 * data: 2023-2-7
 */
public interface InstructionDefinitionRepository extends JpaRepository<InstructionDefinition,Long>,JpaSpecificationExecutor<InstructionDefinition> {
}
