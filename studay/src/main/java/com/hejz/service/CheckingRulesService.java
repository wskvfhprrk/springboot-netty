package com.hejz.service;

import com.hejz.entity.DataCheckingRules;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 08:35
 * @Description:
 */
public interface CheckingRulesService {

    DataCheckingRules getById(Integer id);

    List<DataCheckingRules> getAll();

    List<DataCheckingRules> getByCommonLength(Integer commonLength);

    DataCheckingRules save(DataCheckingRules relay);

    DataCheckingRules update(DataCheckingRules relay);

    void delete(Integer id);
}
