package com.hejz.service;

import com.hejz.dto.CheckingRulesDto;
import com.hejz.dto.CheckingRulesFindByPageDto;
import com.hejz.dto.CheckingRulesUpdateDto;
import com.hejz.entity.CheckingRules;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 08:35
 * @Description:
 */
public interface CheckingRulesService {

    CheckingRules findById(Integer id);

    List<CheckingRules> getAll();

    List<CheckingRules> getByCommonLength(Integer commonLength);

    CheckingRules save(CheckingRulesDto relay);

    CheckingRules update(CheckingRulesUpdateDto relay);

    void delete(Integer id);

    Page<CheckingRules> findPage(CheckingRulesFindByPageDto dto);
}
