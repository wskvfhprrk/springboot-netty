package com.hejz.dtu.service.impl;

import com.hejz.dtu.dto.MenuFindByPageDto;
import com.hejz.dtu.entity.Menu;
import com.hejz.dtu.repository.MenuRepository;
import com.hejz.dtu.service.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public Menu Save(Menu menu) {
        return menuRepository.save(menu);
    }

    @Override
    public void delete(Integer id) {
        menuRepository.deleteById( id);
    }

    @Override
    public Menu findById(Integer id) {
       return menuRepository.findById( id).orElse(null);
    }

    @Override
    public Page<Menu> findPage(MenuFindByPageDto dto) {
        Specification<Menu> sp= (root, query, cb)-> {
            List<Predicate> predicates = new ArrayList<>();
            if(dto.getHidden()!=null) {
            predicates.add(cb.equal(root.get("hidden"), dto.getHidden()));
            }
            if(StringUtils.isNotBlank(dto.getIcon())) {
                predicates.add(cb.like(root.get("icon"), "%"+dto.getIcon()+"%"));
            }
            if(StringUtils.isNotBlank(dto.getName())) {
                predicates.add(cb.like(root.get("name"), "%"+dto.getName()+"%"));
            }
            if(dto.getOrderByNo()!=null && dto.getOrderByNo()!=0) {
            predicates.add(cb.equal(root.get("orderByNo"), dto.getOrderByNo()));
            }
            if(dto.getParentId()!=null && dto.getParentId()!=0) {
            predicates.add(cb.equal(root.get("parentId"), dto.getParentId()));
            }
            if(StringUtils.isNotBlank(dto.getPath())) {
                predicates.add(cb.like(root.get("path"), "%"+dto.getPath()+"%"));
            }
            if(StringUtils.isNotBlank(dto.getTitle())) {
                predicates.add(cb.like(root.get("title"), "%"+dto.getTitle()+"%"));
            }
            if(dto.getType()!=null && dto.getType()!=0) {
            predicates.add(cb.equal(root.get("type"), dto.getType()));
            }
            if(StringUtils.isNotBlank(dto.getUrl())) {
                predicates.add(cb.like(root.get("url"), "%"+dto.getUrl()+"%"));
            }
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        //截取第一个字符，为-是倒序，为+正排序,后面为字段名称
        Sort.Direction direction = dto.getSort().substring(0, 1).equals("+") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, dto.getSort().substring(1));
        Page<Menu> all = menuRepository.findAll(sp, PageRequest.of(dto.getPage(), dto.getLimit(), sort));
        System.out.println(all);
        return all;
    }

}
