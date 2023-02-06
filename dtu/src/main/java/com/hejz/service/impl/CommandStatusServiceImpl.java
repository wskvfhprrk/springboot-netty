package com.hejz.service.impl;

import com.hejz.common.Constant;
import com.hejz.dto.CommandStatusFindByPageDto;
import com.hejz.entity.CommandStatus;
import com.hejz.entity.DtuInfo;
import com.hejz.repository.CommandStatusRepository;
import com.hejz.service.CommandStatusService;
import com.hejz.service.DtuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 07:55
 * @Description:
 */
@Service
public class CommandStatusServiceImpl implements CommandStatusService {
    @Autowired
    private CommandStatusRepository commandStatusRepository;
    @Autowired
    private DtuInfoService dtuInfoService;
    @Autowired
    RedisTemplate redisTemplate;

    @Cacheable(value = Constant.COMMAND_STATUS_CACHE_KEY, key = "#p0", unless = "#result == null")
    @Override
    public List<CommandStatus> findAllByDtuId(Long dtuId) {
        List<CommandStatus> collect = commandStatusRepository.findAllByDtuInfo(dtuInfoService.findById(dtuId)).stream()
                .filter(commandStatus -> commandStatus.getStatus()).collect(Collectors.toList());
        if (collect.isEmpty()) {
            return new ArrayList<>();
        } else {
            return collect;
        }
    }

    @Override
    public CommandStatus findById(Long id) {
        CommandStatus commandStatus = commandStatusRepository.findById(id).orElse(null);
        return commandStatus;
    }

    @CacheEvict(value = Constant.COMMAND_STATUS_CACHE_KEY, key = "#p0.dtuId")
    @Override
    public CommandStatus save(CommandStatus commandStatus) {
        return commandStatusRepository.save(commandStatus);
    }

    @CacheEvict(value = Constant.COMMAND_STATUS_CACHE_KEY, key = "#p0.dtuId")
    @Override
    public CommandStatus update(CommandStatus commandStatus) {
        return commandStatusRepository.save(commandStatus);
    }

    @Override
    public void delete(Long id) {
        CommandStatus commandStatus = commandStatusRepository.findById(id).orElse(null);
        DtuInfo dtuInfo = dtuInfoService.findById(commandStatus.getDtuInfo().getId());
        redisTemplate.delete(Constant.COMMAND_STATUS_CACHE_KEY + "::" + dtuInfo.getId());
        commandStatusRepository.deleteById(id);
    }

    @Override
    public Page<CommandStatus> findPage(CommandStatusFindByPageDto dto) {
        Specification<CommandStatus> sp = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (dto.getDtuId() != null && dto.getDtuId() != 0) {
                predicates.add(cb.equal(root.get("dtuId"), dto.getDtuId()));
            }
            if (dto.getCommonId() != null && dto.getCommonId() != 0) {
                predicates.add(cb.equal(root.get("commonId"), dto.getCommonId()));
            }
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        //截取第一个字符，为-是倒序，为+正排序,后面为字段名称
        Sort.Direction direction = dto.getSort().substring(0, 1).equals("+") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, dto.getSort().substring(1));
        Page<CommandStatus> all = commandStatusRepository.findAll(sp, PageRequest.of(dto.getPage(), dto.getLimit(), sort));
        System.out.println(all);
        return all;
    }

}
