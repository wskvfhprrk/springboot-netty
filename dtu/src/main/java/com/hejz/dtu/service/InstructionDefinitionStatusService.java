package com.hejz.dtu.service;

import com.hejz.dtu.dto.CommandStatusFindByPageDto;
import com.hejz.dtu.entity.InstructionDefinitionStatus;
import com.hejz.dtu.entity.InstructionDefinition;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *
 */
public interface InstructionDefinitionStatusService {
    InstructionDefinitionStatus save(InstructionDefinitionStatus instructionDefinitionStatus);
    void delete(Long id);
    InstructionDefinitionStatus findById(Long id);
    Page<InstructionDefinitionStatus> findPage(CommandStatusFindByPageDto dto);
    List<InstructionDefinitionStatus> findByInstructionDefinition(InstructionDefinition instructionDefinition);

}
