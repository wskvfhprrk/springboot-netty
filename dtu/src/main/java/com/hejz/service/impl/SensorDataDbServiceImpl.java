package com.hejz.service.impl;

import com.hejz.common.Page;
import com.hejz.common.PageResult;
import com.hejz.common.Result;
import com.hejz.entity.SensorDataDb;
import com.hejz.repository.SensorDataDbRepository;
import com.hejz.service.SensorDataDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 07:55
 * @Description:
 */
@Service
public class SensorDataDbServiceImpl implements SensorDataDbService {
    @Autowired
    private SensorDataDbRepository selayRepository;

    @Override
    public List<SensorDataDb> findAllByDtuId(Long dtuId) {
        return selayRepository.findAllByDtuId(dtuId);
    }

    @Override
    public SensorDataDb getById(Long id) {
        SensorDataDb selay = selayRepository.findById(id);
        return selay;
    }

    @Override
    public SensorDataDb save(SensorDataDb selay) {
        return selayRepository.save(selay);
    }

    @Override
    public SensorDataDb update(SensorDataDb selay) {
        return selayRepository.save(selay);
    }

    @Override
    public void delete(Long id) {
        selayRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllByDtuId(Long dutId) {
        selayRepository.deleteAllByDtuId(dutId);
    }

    @Override
    public Result<PageResult> getPage(Page page) {
        Specification<SensorDataDb> sp = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
//            if(user.getId()!=null && user.getId()!=0) {
//                predicates.add(cb.equal(root.get("Id"), user.getId()));
//            }
//            if(StringUtils.isNotBlank(user.getUsername())) {
//                predicates.add(cb.like(root.get("Username"), "%"+user.getUsername()+"%"));
//            }
//            if(user.getAge()!=null && user.getAge()!=0) {
//                predicates.add(cb.equal(root.get("Age"), user.getAge()));
//            }
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        org.springframework.data.domain.Page<SensorDataDb> all = selayRepository.findAll(sp, PageRequest.of(page.getPage(), page.getLimit(), page.getSort()));
        return Result.ok(all);
    }

}
