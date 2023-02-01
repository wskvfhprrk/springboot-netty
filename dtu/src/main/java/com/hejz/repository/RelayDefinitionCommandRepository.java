package com.hejz.repository;

import com.hejz.enm.InstructionTypeEnum;
import com.hejz.entity.RelayDefinitionCommand;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * 用户信息 dao层
 * author: hejz
 * data: 2022-5-9
 */
public interface RelayDefinitionCommandRepository extends CrudRepository<RelayDefinitionCommand,Long>,JpaSpecificationExecutor<RelayDefinitionCommand> {


    List<RelayDefinitionCommand> findByDtuId(Long dtuId);

    void deleteAllByDtuId(Long dtuId);

    List<RelayDefinitionCommand> findByDtuIdAndInstructionTypeEnum(Long dtuId, InstructionTypeEnum instructionTypeEnum);
}
