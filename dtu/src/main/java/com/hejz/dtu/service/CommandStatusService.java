package com.hejz.dtu.service;

import com.hejz.dtu.dto.CommandStatusFindByPageDto;
import com.hejz.dtu.entity.CommandStatus;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *
 */
public interface CommandStatusService {
    CommandStatus Save(CommandStatus commandStatus);
    void delete(Long id);
    CommandStatus findById(Long id);
    Page<CommandStatus> findPage(CommandStatusFindByPageDto dto);
}
