package com.hejz.service.impl;

import com.hejz.common.Constant;
import com.hejz.entity.CheckingRules;
import com.hejz.repository.CheckingRulesRepository;
import com.hejz.service.CheckingRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
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
    private CheckingRulesRepository checkingRulesRepository;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public CheckingRules findById(Integer id) {
        CheckingRules checkingRules = checkingRulesRepository.findById(id).orElse(null);
        return checkingRules;
    }

    @Override
    public List<CheckingRules> getAll() {
        Specification<CheckingRules> spec= (root, query, criteriaBuilder) -> null;
        return checkingRulesRepository.findAll(spec);
    }

    @Cacheable(value = Constant.CHECKING_RULES_CACHE_KEY, key = "#p0", unless = "#result == null")
    @Override
    public List<CheckingRules> getByCommonLength(Integer commonLength) {
        return checkingRulesRepository.findByCommonLength(commonLength);
    }

    @Override
    public CheckingRules save(CheckingRules checkingRules) {
        redisTemplate.delete(Constant.CHECKING_RULES_CACHE_KEY + "::" + checkingRules.getCommonLength());
        checkingRules.setId(null);
        return checkingRulesRepository.save(checkingRules);
    }

    @Override
    public CheckingRules update(CheckingRules checkingRules) {
        redisTemplate.delete(Constant.CHECKING_RULES_CACHE_KEY + "::" + checkingRules.getCommonLength());
        return checkingRulesRepository.save(checkingRules);
    }

    @Override
    public void delete(Integer id) {
        CheckingRules checkingRules = checkingRulesRepository.findById(id).orElse(null);
        redisTemplate.delete(Constant.CHECKING_RULES_CACHE_KEY + "::" + checkingRules.getCommonLength());
        checkingRulesRepository.deleteById(id);
    }

}
