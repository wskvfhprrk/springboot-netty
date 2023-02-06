package com.hejz.service;


import com.hejz.dto.CommandFindByPageDto;
import com.hejz.entity.Command;
import com.hejz.entity.DtuInfo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 08:35
 * @Description:
 */
public interface CommandService {
    List<Command> findAllByDtuId(Long dtuId);

    Command findById(Long id);

    Command save(Command commandStatus);

    Command update(Command commandStatus);

    void delete(Long id);

    Page<Command> findPage(CommandFindByPageDto dto);

}
