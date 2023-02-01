package com.hejz.service;

import com.hejz.common.Result;
import com.hejz.dto.ManualCommandDto;
import com.hejz.dto.RelayFindByPageDto;
import com.hejz.enm.InstructionTypeEnum;
import com.hejz.entity.Relay;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 08:35
 * @Description:
 */
public interface RelayService {
    List<Relay> findAllByDtuId(Long dtuId);

    Relay findById(Long id);

    Relay save(Relay relay);

    Relay update(Relay relay);

    void delete(Long id);

    void deleteAlByDtuId(Long dtuId);

    Page<Relay> findPage(RelayFindByPageDto dto);

    /**
     * 手动指令
     * @param dtuId
     * @return
     */
    Result manualCommand(ManualCommandDto manualCommandDto);

}
