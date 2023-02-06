//package com.hejz.service.impl;
//
//import com.hejz.common.Constant;
//import com.hejz.dto.RelayDefinitionCommandFindByPageDto;
//import com.hejz.enm.InstructionTypeEnum;
//import com.hejz.entity.InstructionDefinition;
//import com.hejz.entity.DtuInfo;
//import com.hejz.repository.InstructionDefinitionRepository;
//import com.hejz.service.DtuInfoService;
//import com.hejz.service.RelayDefinitionCommandService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.criteria.Predicate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
///**
// * @author:hejz 75412985@qq.com
// * @create: 2023-01-12 07:55
// * @Description:
// */
//@Service
//public class RelayDefinitionCommandServiceImpl implements RelayDefinitionCommandService {
//
//    @Autowired
//    private InstructionDefinitionRepository instructionDefinitionRepository;
//    @Autowired
//    private DtuInfoService dtuInfoService;
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    @Cacheable(value = Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY, key = "#p0", unless = "#result == null")
//    @Override
//    public List<InstructionDefinition> findByAllDtuId(Long dtuId) {
//        return instructionDefinitionRepository.findByDtuInfo(dtuInfoService.findById(dtuId));
//    }
//
//    @Override
//    public List<InstructionDefinition> findByAllDtuId(Long dtuId, InstructionTypeEnum instructionTypeEnum) {
//        return instructionDefinitionRepository.findByDtuInfoAndInstructionType(dtuInfoService.findById(dtuId),instructionTypeEnum);
//    }
//
//    @Override
//    public InstructionDefinition findById(Long id) {
//        return instructionDefinitionRepository.findById(id).orElse(null);
//    }
//
//    @CacheEvict(value = Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY, key = "#result.dtuId")
//    @Override
//    public InstructionDefinition save(InstructionDefinition instructionDefinition) {
//        instructionDefinition.setId(null);
//        return instructionDefinitionRepository.save(instructionDefinition);
//    }
//
//    @CacheEvict(value = Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY, key = "#result.dtuId")
//    @Override
//    public InstructionDefinition update(InstructionDefinition instructionDefinition) {
//        return instructionDefinitionRepository.save(instructionDefinition);
//    }
//
//    @Override
//    public void delete(Long id) {
//        Optional<InstructionDefinition> optionalRelayDefinitionCommand = instructionDefinitionRepository.findById(id);
//        if (optionalRelayDefinitionCommand.isPresent()) {
//            DtuInfo dtuInfo = dtuInfoService.findById(optionalRelayDefinitionCommand.get().getDtuInfo().getId());
//            redisTemplate.delete(Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY+"::"+dtuInfo.getId());
//            instructionDefinitionRepository.deleteById(id);
//        }
//    }
//
//    @CacheEvict(value = Constant.RELAY_DEFINITION_COMMAND_CACHE_KEY, key = "#p0")
//    @Override
//    @Transactional
//    public void deleteAllByDtuId(Long dtuId) {
//        List<Long> list = instructionDefinitionRepository.findByDtuInfo(dtuInfoService.findById(dtuId)).stream().map(InstructionDefinition::getId).collect(Collectors.toList());
//        instructionDefinitionRepository.deleteAllById(list);
//    }
//
//    @Override
//    public Page<InstructionDefinition> findPage(RelayDefinitionCommandFindByPageDto dto) {
//        Specification<InstructionDefinition> sp = (root, query, cb) -> {
//            List<Predicate> predicates = new ArrayList<>();
//            if (dto.getDtuId() != null && dto.getDtuId() != 0) {
//                predicates.add(cb.equal(root.get("dtuId"), dto.getDtuId()));
//            }
//            if (dto.getName() != null && dto.getName().length()>0) {
//                predicates.add(cb.equal(root.get("name"), dto.getName()));
//            }
//            Predicate[] andPredicate = new Predicate[predicates.size()];
//            return cb.and(predicates.toArray(andPredicate));
//        };
//        //截取第一个字符，为-是倒序，为+正排序,后面为字段名称
//        Sort.Direction direction = dto.getSort().substring(0, 1).equals("+") ? Sort.Direction.ASC : Sort.Direction.DESC;
//        Sort sort = Sort.by(direction, dto.getSort().substring(1));
//        Page<InstructionDefinition> all = instructionDefinitionRepository.findAll(sp, PageRequest.of(dto.getPage(), dto.getLimit(), sort));
//        System.out.println(all);
//        return all;
//    }
//
//
//}
