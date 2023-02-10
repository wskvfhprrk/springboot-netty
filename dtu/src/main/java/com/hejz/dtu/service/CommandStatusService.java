package com.hejz.dtu.service;

import com.hejz.dtu.dto.CommandStatusFindByPageDto;
import com.hejz.dtu.entity.CommandStatus;
import com.hejz.dtu.entity.DtuInfo;
import com.hejz.dtu.entity.InstructionDefinition;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *
 */
public interface CommandStatusService {
    CommandStatus save(CommandStatus commandStatus);
    void delete(Long id);
    CommandStatus findById(Long id);
    Page<CommandStatus> findPage(CommandStatusFindByPageDto dto);
    CommandStatus findByInstructionDefinition(InstructionDefinition instructionDefinition);

}
