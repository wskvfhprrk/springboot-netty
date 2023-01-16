package com.hejz.service.impl;

import com.hejz.common.Constant;
import com.hejz.entity.CheckingRules;
import com.hejz.repository.DataCheckingRulesRepository;
import com.hejz.service.CheckingRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 07:55
 * @Description:
 */
@Service
public class CheckingRulesServiceImpl implements CheckingRulesService {

    @Autowired
    private DataCheckingRulesRepository dataCheckingRulesRepository;

    @Override
    public CheckingRules getById(Integer id) {
        CheckingRules checkingRules = dataCheckingRulesRepository.getById(id);
        return checkingRules;
    }

    @Override
    public List<CheckingRules> getAll() {
        return dataCheckingRulesRepository.findAll();
    }

    @Cacheable(value = Constant.CHECKING_RULES_CACHE_KEY, key = "#p0", unless = "#result == null")
    @Override
    public List<CheckingRules> getByCommonLength(Integer commonLength) {
        return dataCheckingRulesRepository.findByCommonLength(commonLength);
    }

    @Override
    public CheckingRules save(CheckingRules checkingRules) {
        return dataCheckingRulesRepository.save(checkingRules);
    }

    @Override
    public CheckingRules update(CheckingRules checkingRules) {
        return dataCheckingRulesRepository.save(checkingRules);
    }

    @Override
    public void delete(Integer id) {
        dataCheckingRulesRepository.deleteById(id);
    }
}
