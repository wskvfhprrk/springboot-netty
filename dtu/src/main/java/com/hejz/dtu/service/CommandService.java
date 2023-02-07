package com.hejz.dtu.service;

import com.hejz.dtu.dto.CommandFindByPageDto;
import com.hejz.dtu.entity.Command;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *
 */
public interface CommandService {
    Command Save(Command command);
    void delete(Long id);
    Command findById(Long id);
    Page<Command> findPage(CommandFindByPageDto dto);
}
