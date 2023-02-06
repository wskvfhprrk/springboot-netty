package com.hejz.service.impl;

import com.hejz.dto.CommandFindByPageDto;
import com.hejz.entity.Command;
import com.hejz.entity.DtuInfo;
import com.hejz.repository.CommandRepository;
import com.hejz.service.CommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-02-05 16:12
 * @Description:
 */
@Service
public class CommandServiceImpl implements CommandService {
    @Autowired
    private CommandRepository commandRepository;
    @Override
    public List<Command> findAllByDtuId(Long dtuId) {
        return null;
    }

    @Override
    public Command findById(Long id) {
        return null;
    }

    @Override
    public Command save(Command command) {
        return commandRepository.save(command);
    }

    @Override
    public Command update(Command command) {
        return commandRepository.save(command);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Page<Command> findPage(CommandFindByPageDto dto) {
        return null;
    }

}
