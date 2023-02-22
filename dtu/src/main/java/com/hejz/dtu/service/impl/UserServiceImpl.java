package com.hejz.dtu.service.impl;

import com.hejz.dtu.dto.UserFindAllDto;
import com.hejz.dtu.dto.UserFindByPageDto;
import com.hejz.dtu.entity.User;
import com.hejz.dtu.repository.UserRepository;
import com.hejz.dtu.service.UserService;
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
    public User save(User user) {
        return userRepository.save(user);
    }
    @Override
    public User update(User user) {
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
    public Page<User> findPage(UserFindByPageDto dto) {
        Specification<User> sp= (root, query, cb)-> {
            List<Predicate> predicates = new ArrayList<>();
            if(dto.getAge()!=null && dto.getAge()!=0) {
            predicates.add(cb.equal(root.get("age"), dto.getAge()));
            }
            if(StringUtils.isNotBlank(dto.getUsername())) {
                predicates.add(cb.like(root.get("username"), "%"+dto.getUsername()+"%"));
            }
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        //截取第一个字符，为-是倒序，为+正排序,后面为字段名称
        Sort.Direction direction = dto.getSort().substring(0, 1).equals("+") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, dto.getSort().substring(1));
        Page<User> all = userRepository.findAll(sp, PageRequest.of(dto.getPage(), dto.getLimit(), sort));
        System.out.println(all);
        return all;
    }

    @Override
    public List<User> findAll(UserFindAllDto dto) {
        return userRepository.findAll();
    }
}
