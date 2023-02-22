package com.hejz.dtu.service;

import com.hejz.dtu.dto.CheckingRulesFindByPageDto;
import com.hejz.dtu.entity.CheckingRules;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *
 */
public interface CheckingRulesService {
    CheckingRules save(CheckingRules checkingRules);
    CheckingRules update(CheckingRules checkingRules);
    void delete(Integer id);
    CheckingRules findById(Integer id);
    Page<CheckingRules> findPage(CheckingRulesFindByPageDto dto);
}
