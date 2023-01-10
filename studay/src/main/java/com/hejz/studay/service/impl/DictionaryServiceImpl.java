package com.hejz.studay.service.impl;

import com.hejz.studay.entity.Dictionary;
import com.hejz.studay.repository.DictionaryRepository;
import com.hejz.studay.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public Dictionary Save(Dictionary dictionary) {
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
    public Page<Dictionary> findPage(Dictionary dictionary, int pageNo,int pageSize) {
        Specification<Dictionary> sp= (root, query, cb)-> {
            List<Predicate> predicates = new ArrayList<>();
            if(dictionary.getId()!=null && dictionary.getId()!=0) {
            predicates.add(cb.equal(root.get("Id"), dictionary.getId()));
            }
            if(dictionary.getParentId()!=null && dictionary.getParentId()!=0) {
            predicates.add(cb.equal(root.get("ParentId"), dictionary.getParentId()));
            }
            if(dictionary.getTenantId()!=null && dictionary.getTenantId()!=0) {
            predicates.add(cb.equal(root.get("TenantId"), dictionary.getTenantId()));
            }
            if(StringUtils.isNotBlank(dictionary.getAppModule())) {
                predicates.add(cb.like(root.get("AppModule"), "%"+dictionary.getAppModule()+"%"));
            }
            if(StringUtils.isNotBlank(dictionary.getType())) {
                predicates.add(cb.like(root.get("Type"), "%"+dictionary.getType()+"%"));
            }
            if(StringUtils.isNotBlank(dictionary.getItemName())) {
                predicates.add(cb.like(root.get("ItemName"), "%"+dictionary.getItemName()+"%"));
            }
            if(StringUtils.isNotBlank(dictionary.getItemValue())) {
                predicates.add(cb.like(root.get("ItemValue"), "%"+dictionary.getItemValue()+"%"));
            }
            if(StringUtils.isNotBlank(dictionary.getDescription())) {
                predicates.add(cb.like(root.get("Description"), "%"+dictionary.getDescription()+"%"));
            }
            if(StringUtils.isNotBlank(dictionary.getExtdata())) {
                predicates.add(cb.like(root.get("Extdata"), "%"+dictionary.getExtdata()+"%"));
            }
            if(dictionary.getSortId()!=null && dictionary.getSortId()!=0) {
            predicates.add(cb.equal(root.get("SortId"), dictionary.getSortId()));
            }
            if(dictionary.getIsEditable()!=null && dictionary.getIsEditable()!=0) {
            predicates.add(cb.equal(root.get("IsEditable"), dictionary.getIsEditable()));
            }
            if(dictionary.getIsDeletable()!=null && dictionary.getIsDeletable()!=0) {
            predicates.add(cb.equal(root.get("IsDeletable"), dictionary.getIsDeletable()));
            }
            if(dictionary.getIsDeleted()!=null && dictionary.getIsDeleted()!=0) {
            predicates.add(cb.equal(root.get("IsDeleted"), dictionary.getIsDeleted()));
            }
            if(dictionary.getCreateTime()!=null) {
            predicates.add(cb.equal(root.get("CreateTime"), dictionary.getCreateTime()));
            }
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        Page<Dictionary> all = dictionaryRepository.findAll(sp, PageRequest.of(pageNo,pageSize));
        System.out.println(all);
        return all;
    }

    @Override
    public List<Dictionary> findAll(Dictionary dictionary) {
        Specification<Dictionary> spec= (root, query, cb)-> {
            List<Predicate> predicates = new ArrayList<>();
            if(dictionary.getId()!=null && dictionary.getId()!=0) {
            predicates.add(cb.equal(root.get("Id"), dictionary.getId()));
            }
            if(dictionary.getParentId()!=null && dictionary.getParentId()!=0) {
            predicates.add(cb.equal(root.get("ParentId"), dictionary.getParentId()));
            }
            if(dictionary.getTenantId()!=null && dictionary.getTenantId()!=0) {
            predicates.add(cb.equal(root.get("TenantId"), dictionary.getTenantId()));
            }
            if(StringUtils.isNotBlank(dictionary.getAppModule())) {
            predicates.add(cb.like(root.get("AppModule"), "%"+dictionary.getAppModule()+"%"));
            }
            if(StringUtils.isNotBlank(dictionary.getType())) {
            predicates.add(cb.like(root.get("Type"), "%"+dictionary.getType()+"%"));
            }
            if(StringUtils.isNotBlank(dictionary.getItemName())) {
            predicates.add(cb.like(root.get("ItemName"), "%"+dictionary.getItemName()+"%"));
            }
            if(StringUtils.isNotBlank(dictionary.getItemValue())) {
            predicates.add(cb.like(root.get("ItemValue"), "%"+dictionary.getItemValue()+"%"));
            }
            if(StringUtils.isNotBlank(dictionary.getDescription())) {
            predicates.add(cb.like(root.get("Description"), "%"+dictionary.getDescription()+"%"));
            }
            if(StringUtils.isNotBlank(dictionary.getExtdata())) {
            predicates.add(cb.like(root.get("Extdata"), "%"+dictionary.getExtdata()+"%"));
            }
            if(dictionary.getSortId()!=null && dictionary.getSortId()!=0) {
            predicates.add(cb.equal(root.get("SortId"), dictionary.getSortId()));
            }
            if(dictionary.getIsEditable()!=null && dictionary.getIsEditable()!=0) {
            predicates.add(cb.equal(root.get("IsEditable"), dictionary.getIsEditable()));
            }
            if(dictionary.getIsDeletable()!=null && dictionary.getIsDeletable()!=0) {
            predicates.add(cb.equal(root.get("IsDeletable"), dictionary.getIsDeletable()));
            }
            if(dictionary.getIsDeleted()!=null && dictionary.getIsDeleted()!=0) {
            predicates.add(cb.equal(root.get("IsDeleted"), dictionary.getIsDeleted()));
            }
            if(dictionary.getCreateTime()!=null) {
            predicates.add(cb.equal(root.get("CreateTime"), dictionary.getCreateTime()));
            }
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        List<Dictionary> all = dictionaryRepository.findAll(spec);
        return all;
    }
}
