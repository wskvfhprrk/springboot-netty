package com.hejz.dtu.service;

import com.hejz.dtu.dto.CommandAllDto;
import com.hejz.dtu.dto.CommandFindByPageDto;
import com.hejz.dtu.entity.Command;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 *
 */
public interface CommandService {
    Command save(Command command);
    Command update(Command command);
    void delete(Long id);
    Command findById(Long id);
    Page<Command> findPage(CommandFindByPageDto dto);

    List<Command> findAll(CommandAllDto dto);

}
