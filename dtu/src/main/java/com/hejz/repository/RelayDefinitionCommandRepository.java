package com.hejz.repository;

import com.hejz.enm.InstructionTypeEnum;
import com.hejz.entity.DtuInfo;
import com.hejz.entity.InstructionDefinition;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * 用户信息 dao层
 * author: hejz
 * data: 2022-5-9
 */
public interface RelayDefinitionCommandRepository extends CrudRepository<InstructionDefinition,Long>,JpaSpecificationExecutor<InstructionDefinition> {


    List<InstructionDefinition> findByDtuInfo(DtuInfo dtuInfo);


    List<InstructionDefinition> findByDtuInfoAndInstructionType(DtuInfo dtuInfo, InstructionTypeEnum instructionTypeEnum);
}
