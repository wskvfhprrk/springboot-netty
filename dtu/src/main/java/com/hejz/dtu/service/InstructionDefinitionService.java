package com.hejz.dtu.service;

import com.hejz.dtu.dto.InstructionDefinitionFindByPageDto;
import com.hejz.dtu.entity.InstructionDefinition;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *
 */
public interface InstructionDefinitionService {
    InstructionDefinition Save(InstructionDefinition instructionDefinition);
    void delete(Long id);
    InstructionDefinition findById(Long id);
    Page<InstructionDefinition> findPage(InstructionDefinitionFindByPageDto dto);
}
