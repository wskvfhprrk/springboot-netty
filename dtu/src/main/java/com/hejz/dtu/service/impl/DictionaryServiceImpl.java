package com.hejz.dtu.service.impl;

import com.hejz.dtu.common.Result;
import com.hejz.dtu.dto.*;
import com.hejz.dtu.enm.DictionaryTypeEnum;
import com.hejz.dtu.entity.Dictionary;
import com.hejz.dtu.repository.DictionaryRepository;
import com.hejz.dtu.service.DictionaryService;
import com.hejz.dtu.vo.DictionaryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
        //判断如果低级没有的话直接改状态为未使用，可以删除
        Dictionary dictionary = dictionaryRepository.findById(id).orElse(null);
        dictionaryRepository.deleteById(id);
        List<Dictionary> all = dictionaryRepository.findAllByDictionary(new Dictionary(dictionary.getDictionary().getId()));
        all.remove(dictionary);
        if(all.isEmpty()){
            dictionary=dictionaryRepository.findById(dictionary.getDictionary().getId()).orElse(null);
            dictionary.setIsUse(false);
            dictionaryRepository.save(dictionary);
        }

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
            if(StringUtils.isNotBlank(dto.getType())) {
                predicates.add(cb.equal(root.get("type"), DictionaryTypeEnum.valueOf(dto.getType())));
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
        return all;
    }

    @Override
    public List<Dictionary> findAll(DictionaryAllDto dto) {
        Specification<Dictionary> spec= (root, query, cb)-> {
            List<Predicate> predicates = new ArrayList<>();
            if(dto.getId()!=null && dto.getId()!=0) {
                predicates.add(cb.equal(root.get("Id"), dto.getId()));
            }
            if(StringUtils.isNotBlank(dto.getAppModule())) {
                predicates.add(cb.like(root.get("appModule"), "%"+dto.getAppModule()+"%"));
            }
            if(dto.getCreateTime()!= null ) {
                predicates.add(cb.equal(root.get("CreateTime"), dto.getCreateTime()));
            }
            if(StringUtils.isNotBlank(dto.getDescription())) {
                predicates.add(cb.like(root.get("Description"), "%"+dto.getDescription()+"%"));
            }
            if(dto.getIsUse()!= null ) {
                predicates.add(cb.equal(root.get("IsUse"), dto.getIsUse()));
            }
            if(StringUtils.isNotBlank(dto.getItemName())) {
                predicates.add(cb.like(root.get("ItemName"), "%"+dto.getItemName()+"%"));
            }
            if(StringUtils.isNotBlank(dto.getItemValue())) {
                predicates.add(cb.like(root.get("ItemValue"), "%"+dto.getItemValue()+"%"));
            }
            if(dto.getSortId()!=null && dto.getSortId()!=0) {
                predicates.add(cb.equal(root.get("SortId"), dto.getSortId()));
            }
            if(StringUtils.isNotBlank(dto.getType())) {
                predicates.add(cb.equal(root.get("type"), DictionaryTypeEnum.valueOf(dto.getType())));
            }
            if(dto.getParentId()!=null && dto.getParentId()!=0) {
                predicates.add(cb.equal(root.get("ParentId"), dto.getParentId()));
            }
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        List<Dictionary> all = dictionaryRepository.findAll(spec);
        return all;
    }

    @Override
    public Result getParent() {
        Specification<Dictionary> ex= (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.or(cb.equal(root.get("type"), DictionaryTypeEnum.TOP_LEVEL),cb.equal(root.get("type"),DictionaryTypeEnum.CLASS_A)));
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        List<Dictionary> all = dictionaryRepository.findAll(ex);
        Stream<DictionaryVo> vos = all.stream().map(dictionary -> {
            DictionaryVo vo = new DictionaryVo();
            BeanUtils.copyProperties(dictionary, vo);
            vo.setParentId(dictionary.getDictionary().getId());
            return vo;
        });
        return Result.ok(vos);
    }

    @Override
    public List<Dictionary> findAllByDictionary(Dictionary dto) {
        return dictionaryRepository.findAllByDictionary(dto);
    }
}
