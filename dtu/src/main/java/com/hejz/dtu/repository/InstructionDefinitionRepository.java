package com.hejz.dtu.repository;

import com.hejz.dtu.enm.InstructionTypeEnum;
import com.hejz.dtu.entity.DtuInfo;
import com.hejz.dtu.entity.InstructionDefinition;
import org.springframework.beans.PropertyValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 继电器定义指令 dao层
 * author: hejz
 * data: 2023-2-7
 */
public interface InstructionDefinitionRepository extends JpaRepository<InstructionDefinition,Long>,JpaSpecificationExecutor<InstructionDefinition> {
    InstructionDefinition findByDtuInfoAndInstructionType(DtuInfo dtuInfo, InstructionTypeEnum instructionTypeEnum);

     List<InstructionDefinition> findAllByDtuInfo(DtuInfo dtuInfo);
}
