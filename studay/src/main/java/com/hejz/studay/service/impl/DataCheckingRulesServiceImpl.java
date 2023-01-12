package com.hejz.studay.service.impl;

import com.hejz.studay.common.Constant;
import com.hejz.studay.entity.DataCheckingRules;
import com.hejz.studay.repository.DataCheckingRulesRepository;
import com.hejz.studay.service.DataCheckingRulesService;
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
public class DataCheckingRulesServiceImpl implements DataCheckingRulesService {
    @Autowired
    private DataCheckingRulesRepository dataCheckingRulesRepository;

    @Override
    public DataCheckingRules getById(Integer id) {
        DataCheckingRules dataCheckingRules = dataCheckingRulesRepository.getById(id);
        return dataCheckingRules;
    }

    @Override
    public List<DataCheckingRules> getAll() {
        return dataCheckingRulesRepository.findAll();
    }

    @Cacheable(value = Constant.DATA_CHECKING_RULES, key = "#p0")
    @Override
    public List<DataCheckingRules> getByCommonLength(Integer commonLength) {
        return dataCheckingRulesRepository.findByCommonLength(commonLength);
    }

    @Override
    public DataCheckingRules save(DataCheckingRules dataCheckingRules) {
        return dataCheckingRulesRepository.save(dataCheckingRules);
    }

    @Override
    public DataCheckingRules update(DataCheckingRules dataCheckingRules) {
        return dataCheckingRulesRepository.save(dataCheckingRules);
    }

    @Override
    public void delete(Integer id) {
        dataCheckingRulesRepository.deleteById(id);
    }
}
