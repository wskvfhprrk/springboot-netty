package com.hejz.dtu.service.impl;

import com.hejz.dtu.dto.DictionaryFindByPageDto;
import com.hejz.dtu.entity.Dictionary;
import com.hejz.dtu.repository.DictionaryRepository;
import com.hejz.dtu.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DictionaryServiceImpl implements DictionaryService {

    @Autowired
    private DictionaryRepository dictionaryRepository;

    @Override
    public Dictionary save(Dictionary dictionary) {
        return dictionaryRepository.save(dictionary);
    }

    @Override
    public Dictionary update(Dictionary dictionary) {
        return dictionaryRepository.save(dictionary);
    }

    @Override
    public void delete(Long id) {
        dictionaryRepository.deleteById( id);
    }

    @Override
    public Dictionary findById(Long id) {
       return dictionaryRepository.findById( id).orElse(null);
    }

    @Override
    public Page<Dictionary> findPage(DictionaryFindByPageDto dto) {
        Specification<Dictionary> sp= (root, query, cb)-> {
            List<Predicate> predicates = new ArrayList<>();
            if(StringUtils.isNotBlank(dto.getAppModule())) {
                predicates.add(cb.like(root.get("appModule"), "%"+dto.getAppModule()+"%"));
            }
            if(dto.getCreateTime()!=null) {
            predicates.add(cb.equal(root.get("createTime"), dto.getCreateTime()));
            }
            if(StringUtils.isNotBlank(dto.getDescription())) {
                predicates.add(cb.like(root.get("description"), "%"+dto.getDescription()+"%"));
            }
            if(dto.getIsUse()!=null) {
            predicates.add(cb.equal(root.get("isUse"), dto.getIsUse()));
            }
            if(StringUtils.isNotBlank(dto.getItemName())) {
                predicates.add(cb.like(root.get("itemName"), "%"+dto.getItemName()+"%"));
            }
            if(StringUtils.isNotBlank(dto.getItemValue())) {
                predicates.add(cb.like(root.get("itemValue"), "%"+dto.getItemValue()+"%"));
            }
            if(dto.getSortId()!=null && dto.getSortId()!=0) {
            predicates.add(cb.equal(root.get("sortId"), dto.getSortId()));
            }
            if(dto.getType()!=null && dto.getType()!=0) {
            predicates.add(cb.equal(root.get("type"), dto.getType()));
            }
            if(dto.getParentId()!=null && dto.getParentId()!=0) {
            predicates.add(cb.equal(root.get("parentId"), dto.getParentId()));
            }
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        //截取第一个字符，为-是倒序，为+正排序,后面为字段名称
        Sort.Direction direction = dto.getSort().substring(0, 1).equals("+") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, dto.getSort().substring(1));
        Page<Dictionary> all = dictionaryRepository.findAll(sp, PageRequest.of(dto.getPage(), dto.getLimit(), sort));
        System.out.println(all);
        return all;
    }

}
