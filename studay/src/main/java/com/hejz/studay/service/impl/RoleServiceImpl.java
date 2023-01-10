package com.hejz.studay.service.impl;

import com.hejz.studay.entity.Role;
import com.hejz.studay.repository.RoleRepository;
import com.hejz.studay.service.RoleService;
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
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role Save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void delete(Integer id) {
        roleRepository.deleteById( id);
    }

    @Override
    public Role findById(Integer id) {
       return roleRepository.findById( id).orElse(null);
    }

    @Override
    public Page<Role> findPage(Role role, int pageNo,int pageSize) {
        Specification<Role> sp= (root, query, cb)-> {
            List<Predicate> predicates = new ArrayList<>();
            if(role.getId()!=null && role.getId()!=0) {
            predicates.add(cb.equal(root.get("Id"), role.getId()));
            }
            if(StringUtils.isNotBlank(role.getName())) {
                predicates.add(cb.like(root.get("Name"), "%"+role.getName()+"%"));
            }
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        Page<Role> all = roleRepository.findAll(sp, PageRequest.of(pageNo,pageSize));
        System.out.println(all);
        return all;
    }

    @Override
    public List<Role> findAll(Role role) {
        Specification<Role> spec= (root, query, cb)-> {
            List<Predicate> predicates = new ArrayList<>();
            if(role.getId()!=null && role.getId()!=0) {
            predicates.add(cb.equal(root.get("Id"), role.getId()));
            }
            if(StringUtils.isNotBlank(role.getName())) {
            predicates.add(cb.like(root.get("Name"), "%"+role.getName()+"%"));
            }
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        List<Role> all = roleRepository.findAll(spec);
        return all;
    }
}
