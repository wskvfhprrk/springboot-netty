package com.hejz.service;


import com.hejz.dto.CommandStatusFindByPageDto;
import com.hejz.entity.CommandStatus;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 08:35
 * @Description:
 */
public interface CommandStatusService {
    List<CommandStatus> findAllByDtuId(Long dtuId);

    CommandStatus findById(Long id);

    CommandStatus save(CommandStatus commandStatus);

    CommandStatus update(CommandStatus commandStatus);

    void delete(Long id);

    Page<CommandStatus> findPage(CommandStatusFindByPageDto dto);
}
