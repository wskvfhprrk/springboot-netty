package com.hejz.service;


import com.hejz.dto.RelayDefinitionCommandFindByPageDto;
import com.hejz.enm.InstructionTypeEnum;
import com.hejz.entity.RelayDefinitionCommand;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 08:35
 * @Description:
 */
public interface RelayDefinitionCommandService {
    List<RelayDefinitionCommand> findByAllDtuId(Long dtuId);
    List<RelayDefinitionCommand> findByAllDtuId(Long dtuId, InstructionTypeEnum instructionTypeEnum);

    RelayDefinitionCommand findById(Long id);

    RelayDefinitionCommand save(RelayDefinitionCommand relayDefinitionCommand);

    RelayDefinitionCommand update(RelayDefinitionCommand relayDefinitionCommand);

    void delete(Long id);

    void deleteAllByDtuId(Long dtuId);

    Page<RelayDefinitionCommand> findPage(RelayDefinitionCommandFindByPageDto dto);


}
