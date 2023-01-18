package com.hejz.service;

import com.hejz.entity.CheckingRules;

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

    CheckingRules save(CheckingRules relay);

    CheckingRules update(CheckingRules relay);

    void delete(Integer id);
}
