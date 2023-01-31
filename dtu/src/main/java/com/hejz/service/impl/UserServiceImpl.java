package com.hejz.service.impl;

import com.hejz.dto.UserFindByPageDto;
import com.hejz.entity.User;
import com.hejz.repository.UserRepository;
import com.hejz.service.UserService;
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
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        List<User> all = userRepository.findAll(spec);
        return all;
    }

    @Override
    public Page<User> findPage(UserFindByPageDto dto) {
        Specification<User> sp= (root, query, cb)-> {
            List<Predicate> predicates = new ArrayList<>();
            if(StringUtils.isNotBlank(dto.getUsername())) {
                predicates.add(cb.like(root.get("Username"), "%"+dto.getUsername()+"%"));
            }
            if(dto.getAge()!=null && dto.getAge()!=0) {
                predicates.add(cb.equal(root.get("Age"), dto.getAge()));
            }
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        //截取第一个字符，为-是倒序，为+正排序,后面为字段名称
        Sort.Direction direction = dto.getSort().substring(0, 1).equals("+") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, dto.getSort().substring(1));
        Page<User> all = userRepository.findAll(sp, PageRequest.of(dto.getPage(),dto.getLimit(),sort));
        System.out.println(all);
        return all;
    }
}
