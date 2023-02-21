package com.hejz.dtu.service.impl;

import com.hejz.dtu.dto.CheckingRulesFindAllDto;
import com.hejz.dtu.dto.CheckingRulesFindByPageDto;
import com.hejz.dtu.entity.CheckingRules;
import com.hejz.dtu.repository.CheckingRulesRepository;
import com.hejz.dtu.service.CheckingRulesService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CheckingRulesServiceImpl implements CheckingRulesService {

    @Autowired
    private CheckingRulesRepository checkingRulesRepository;

    @Override
    public CheckingRules save(CheckingRules checkingRules) {
        return checkingRulesRepository.save(checkingRules);
    }

    @Override
    public CheckingRules update(CheckingRules checkingRules) {
        return checkingRulesRepository.save(checkingRules);
    }

    @Override
    public void delete(Integer id) {
        checkingRulesRepository.deleteById(id);
    }

    @Override
    public CheckingRules findById(Integer id) {
        return checkingRulesRepository.findById(id).orElse(null);
    }

    @Override
    public Page<CheckingRules> findPage(CheckingRulesFindByPageDto dto) {
        Specification<CheckingRules> sp= (root, query, cb)-> {
            List<Predicate> predicates = new ArrayList<>();
            if(dto.getDataBitsLength()!=null && dto.getDataBitsLength()!=0) {
            predicates.add(cb.equal(root.get("dataBitsLength"), dto.getDataBitsLength()));
            }
            if(dto.getDataValueLength()!=null && dto.getDataValueLength()!=0) {
            predicates.add(cb.equal(root.get("dataValueLength"), dto.getDataValueLength()));
            }
            if(dto.getCommonLength()!=null && dto.getCommonLength()!=0) {
            predicates.add(cb.equal(root.get("commonLength"), dto.getCommonLength()));
            }
            if(dto.getCrc16CheckDigitLength()!=null && dto.getCrc16CheckDigitLength()!=0) {
            predicates.add(cb.equal(root.get("crc16CheckDigitLength"), dto.getCrc16CheckDigitLength()));
            }
            if(dto.getFunctionCodeLength()!=null && dto.getFunctionCodeLength()!=0) {
            predicates.add(cb.equal(root.get("functionCodeLength"), dto.getFunctionCodeLength()));
            }
            if(dto.getIsUse()!=null) {
            predicates.add(cb.equal(root.get("isUse"), dto.getIsUse()));
            }
            if(StringUtils.isNotBlank(dto.getName())) {
                predicates.add(cb.like(root.get("name"), "%"+dto.getName()+"%"));
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
    @Override
    public List<CheckingRules> findAll(CheckingRulesFindAllDto dto) {
        Specification<CheckingRules> spec= (root, query, cb)-> {
            List<Predicate> predicates = new ArrayList<>();
            if(dto.getId()!=null && dto.getId()!=0) {
                predicates.add(cb.equal(root.get("Id"), dto.getId()));
            }
            if(dto.getAddressBitLength()!=null && dto.getAddressBitLength()!=0) {
                predicates.add(cb.equal(root.get("AddressBitLength"), dto.getAddressBitLength()));
            }
            if(dto.getCommonLength()!=null && dto.getCommonLength()!=0) {
                predicates.add(cb.equal(root.get("CommonLength"), dto.getCommonLength()));
            }
            if(dto.getCrc16CheckDigitLength()!=null && dto.getCrc16CheckDigitLength()!=0) {
                predicates.add(cb.equal(root.get("Crc16CheckDigitLength"), dto.getCrc16CheckDigitLength()));
            }
            if(dto.getDataBitsLength()!=null && dto.getDataBitsLength()!=0) {
                predicates.add(cb.equal(root.get("DataBitsLength"), dto.getDataBitsLength()));
            }
            if(dto.getDataValueLength()!=null && dto.getDataValueLength()!=0) {
                predicates.add(cb.equal(root.get("DataValueLength"), dto.getDataValueLength()));
            }
            if(dto.getFunctionCodeLength()!=null && dto.getFunctionCodeLength()!=0) {
                predicates.add(cb.equal(root.get("FunctionCodeLength"), dto.getFunctionCodeLength()));
            }
            if(dto.getIsUse()!= null ) {
                predicates.add(cb.equal(root.get("IsUse"), dto.getIsUse()));
            }
            if(StringUtils.isNotBlank(dto.getName())) {
                predicates.add(cb.like(root.get("Name"), "%"+dto.getName()+"%"));
            }
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        List<CheckingRules> all = checkingRulesRepository.findAll(spec);
        return all;
    }

}
