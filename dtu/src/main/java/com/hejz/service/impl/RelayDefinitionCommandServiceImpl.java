package com.hejz.service.impl;

import com.hejz.common.Constant;
import com.hejz.dto.RelayDefinitionCommandFindByPageDto;
import com.hejz.enm.InstructionTypeEnum;
import com.hejz.entity.RelayDefinitionCommand;
import com.hejz.entity.RelayDefinitionCommand;
import com.hejz.entity.DtuInfo;
import com.hejz.entity.RelayDefinitionCommand;
import com.hejz.repository.RelayDefinitionCommandRepository;
import com.hejz.service.DtuInfoService;
import com.hejz.service.RelayDefinitionCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 07:55
 * @Description:
 */
@Service
public class RelayDefinitionCommandServiceImpl implements RelayDefinitionCommandService {

    @Autowired
    private RelayDefinitionCommandRepository relayDefinitionCommandRepository;
    @Autowired
    private DtuInfoService dtuInfoService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Cacheable(value = Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY, key = "#p0", unless = "#result == null")
    @Override
    public List<RelayDefinitionCommand> findByAllDtuId(Long dtuId) {
        return relayDefinitionCommandRepository.findByDtuId(dtuId);
    }

    @Override
    public List<RelayDefinitionCommand> findByAllDtuId(Long dtuId, InstructionTypeEnum instructionTypeEnum) {
        return relayDefinitionCommandRepository.findByDtuIdAndInstructionTypeEnum(dtuId,instructionTypeEnum);
    }

    @Override
    public RelayDefinitionCommand findById(Long id) {
        return relayDefinitionCommandRepository.findById(id).orElse(null);
    }

    @CacheEvict(value = Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY, key = "#result.dtuId")
    @Override
    public RelayDefinitionCommand save(RelayDefinitionCommand relayDefinitionCommand) {
        relayDefinitionCommand.setId(null);
        return relayDefinitionCommandRepository.save(relayDefinitionCommand);
    }

    @CacheEvict(value = Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY, key = "#result.dtuId")
    @Override
    public RelayDefinitionCommand update(RelayDefinitionCommand relayDefinitionCommand) {
        return relayDefinitionCommandRepository.save(relayDefinitionCommand);
    }

    @Override
    public void delete(Long id) {
        Optional<RelayDefinitionCommand> optionalRelayDefinitionCommand = relayDefinitionCommandRepository.findById(id);
        if (optionalRelayDefinitionCommand.isPresent()) {
            DtuInfo dtuInfo = dtuInfoService.findById(optionalRelayDefinitionCommand.get().getDtuId());
            redisTemplate.delete(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY+"::"+dtuInfo.getId());
            relayDefinitionCommandRepository.deleteById(id);
        }
    }

    @CacheEvict(value = Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY, key = "#p0")
    @Override
    @Transactional
    public void deleteAllByDtuId(Long dtuId) {
        relayDefinitionCommandRepository.deleteAllByDtuId(dtuId);
    }

    @Override
    public Page<RelayDefinitionCommand> findPage(RelayDefinitionCommandFindByPageDto dto) {
        Specification<RelayDefinitionCommand> sp = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (dto.getDtuId() != null && dto.getDtuId() != 0) {
                predicates.add(cb.equal(root.get("dtuId"), dto.getDtuId()));
            }
            if (dto.getName() != null && dto.getName().length()>0) {
                predicates.add(cb.equal(root.get("name"), dto.getName()));
            }
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        //截取第一个字符，为-是倒序，为+正排序,后面为字段名称
        Sort.Direction direction = dto.getSort().substring(0, 1).equals("+") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, dto.getSort().substring(1));
        Page<RelayDefinitionCommand> all = relayDefinitionCommandRepository.findAll(sp, PageRequest.of(dto.getPage(), dto.getLimit(), sort));
        System.out.println(all);
        return all;
    }


}
