package com.hejz.service.impl;

import com.hejz.common.Constant;
import com.hejz.dto.CheckingRulesDto;
import com.hejz.dto.CheckingRulesFindByPageDto;
import com.hejz.dto.CheckingRulesUpdateDto;
import com.hejz.entity.CheckingRules;
import com.hejz.repository.CheckingRulesRepository;
import com.hejz.service.CheckingRulesService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
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

    @Cacheable(cacheNames = Constant.CHECKING_RULES_CACHE_KEY,key = "#p0" , unless = "#result == null")
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

    @Override
    public List<CheckingRules> getByCommonLength(Integer commonLength) {
        return checkingRulesRepository.findByCommonLength(commonLength);
    }

    @Override
    public CheckingRules save(CheckingRulesDto checkingRulesDto) {
        CheckingRules checkingRules = new CheckingRules();
        BeanUtils.copyProperties(checkingRulesDto, checkingRules);
        return checkingRulesRepository.save(checkingRules);
    }

    @Override
    public CheckingRules update(CheckingRulesUpdateDto checkingRulesDto) {
        CheckingRules checkingRules = new CheckingRules();
        BeanUtils.copyProperties(checkingRulesDto, checkingRules);
        redisTemplate.delete(Constant.CHECKING_RULES_CACHE_KEY + "::" + checkingRules.getCommonLength());
        return checkingRulesRepository.save(checkingRules);
    }

    @Override
    public void delete(Integer id) {
        CheckingRules checkingRules = checkingRulesRepository.findById(id).orElse(null);
        redisTemplate.delete(Constant.CHECKING_RULES_CACHE_KEY + "::" + checkingRules.getId());
        checkingRulesRepository.deleteById(id);
    }

    @Override
    public Page<CheckingRules> findPage(CheckingRulesFindByPageDto dto) {
        Specification<CheckingRules> sp = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (dto.getName() != null && dto.getName().length() > 0) {
                predicates.add(cb.like(root.get("name"), "%" + dto.getName() + "%"));
            }
            if (dto.getCommonLength() != null && dto.getCommonLength() != 0) {
                predicates.add(cb.equal(root.get("commonLength"), dto.getCommonLength()));
            }
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        //截取第一个字符，为-是倒序，为+正排序,后面为字段名称
        Sort.Direction direction = dto.getSort().substring(0, 1).equals("+") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, dto.getSort().substring(1));
        Page<CheckingRules> all = checkingRulesRepository.findAll(sp, PageRequest.of(dto.getPage(), dto.getLimit(), sort));
        System.out.println(all);
        return all;
    }

}
