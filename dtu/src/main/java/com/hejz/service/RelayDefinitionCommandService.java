package com.hejz.service;


import com.hejz.dto.RelayDefinitionCommandFindByPageDto;
import com.hejz.enm.InstructionTypeEnum;
import com.hejz.entity.InstructionDefinition;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 08:35
 * @Description:
 */
public interface RelayDefinitionCommandService {
    List<InstructionDefinition> findByAllDtuId(Long dtuId);
    List<InstructionDefinition> findByAllDtuId(Long dtuId, InstructionTypeEnum instructionTypeEnum);

    InstructionDefinition findById(Long id);

    InstructionDefinition save(InstructionDefinition instructionDefinition);

    InstructionDefinition update(InstructionDefinition instructionDefinition);

    void delete(Long id);

    void deleteAllByDtuId(Long dtuId);

    Page<InstructionDefinition> findPage(RelayDefinitionCommandFindByPageDto dto);


}
