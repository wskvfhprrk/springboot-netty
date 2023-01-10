package com.hejz.studay.service.impl;

import com.hejz.studay.entity.User;
import com.hejz.studay.repository.UserRepository;
import com.hejz.studay.service.UserService;
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
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User Save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById( id);
    }

    @Override
    public User findById(Integer id) {
       return userRepository.findById( id).orElse(null);
    }

    @Override
    public Page<User> findPage(User user, int pageNo,int pageSize) {
        Specification<User> sp= (root, query, cb)-> {
            List<Predicate> predicates = new ArrayList<>();
            if(user.getId()!=null && user.getId()!=0) {
            predicates.add(cb.equal(root.get("Id"), user.getId()));
            }
            if(StringUtils.isNotBlank(user.getUsername())) {
                predicates.add(cb.like(root.get("Username"), "%"+user.getUsername()+"%"));
            }
            if(user.getAge()!=null && user.getAge()!=0) {
            predicates.add(cb.equal(root.get("Age"), user.getAge()));
            }
            if(user.getRoleId()!=null && user.getRoleId()!=0) {
            predicates.add(cb.equal(root.get("RoleId"), user.getRoleId()));
            }
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        Page<User> all = userRepository.findAll(sp, PageRequest.of(pageNo,pageSize));
        System.out.println(all);
        return all;
    }

    @Override
    public List<User> findAll(User user) {
        Specification<User> spec= (root, query, cb)-> {
            List<Predicate> predicates = new ArrayList<>();
            if(user.getId()!=null && user.getId()!=0) {
            predicates.add(cb.equal(root.get("Id"), user.getId()));
            }
            if(StringUtils.isNotBlank(user.getUsername())) {
            predicates.add(cb.like(root.get("Username"), "%"+user.getUsername()+"%"));
            }
            if(user.getAge()!=null && user.getAge()!=0) {
            predicates.add(cb.equal(root.get("Age"), user.getAge()));
            }
            if(user.getRoleId()!=null && user.getRoleId()!=0) {
            predicates.add(cb.equal(root.get("RoleId"), user.getRoleId()));
            }
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        List<User> all = userRepository.findAll(spec);
        return all;
    }
}
