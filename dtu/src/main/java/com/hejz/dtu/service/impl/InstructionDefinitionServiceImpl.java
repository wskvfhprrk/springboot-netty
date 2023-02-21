package com.hejz.dtu.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hejz.dtu.common.Constant;
import com.hejz.dtu.common.Result;
import com.hejz.dtu.dto.DendManuallyDto;
import com.hejz.dtu.dto.InstructionDefinitionAllDto;
import com.hejz.dtu.dto.InstructionDefinitionFindByPageDto;
import com.hejz.dtu.enm.InstructionTypeEnum;
import com.hejz.dtu.entity.DtuInfo;
import com.hejz.dtu.entity.InstructionDefinition;
import com.hejz.dtu.nettyserver.NettyServiceCommon;
import com.hejz.dtu.repository.InstructionDefinitionRepository;
import com.hejz.dtu.service.DtuInfoService;
import com.hejz.dtu.service.InstructionDefinitionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InstructionDefinitionServiceImpl implements InstructionDefinitionService {

    @Autowired
    private InstructionDefinitionRepository instructionDefinitionRepository;
    @Autowired
    private DtuInfoService dtuInfoService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public InstructionDefinition save(InstructionDefinition instructionDefinition) {
        return instructionDefinitionRepository.save(instructionDefinition);
    }
    @Override
    public InstructionDefinition update(InstructionDefinition instructionDefinition) {
        return instructionDefinitionRepository.save(instructionDefinition);
    }

    @Override
    public void delete(Long id) {
        instructionDefinitionRepository.deleteById(id);
    }

    @Override
    public InstructionDefinition findById(Long id) {
       return instructionDefinitionRepository.findById( id).orElse(null);
    }

    @Override
    public Page<InstructionDefinition> findPage(InstructionDefinitionFindByPageDto dto) {
        Specification<InstructionDefinition> sp= (root, query, cb)-> {
            List<Predicate> predicates = new ArrayList<>();
            Join<InstructionDefinition,DtuInfo> join=root.join("dtuInfo", JoinType.LEFT);
            if(dto.getInstructionType()!=null ) {
            predicates.add(cb.equal(root.get("instructionType"), dto.getInstructionType()));
            }
            if(StringUtils.isNotBlank(dto.getName())) {
                predicates.add(cb.like(root.get("name"), "%"+dto.getName()+"%"));
            }
            if(StringUtils.isNotBlank(dto.getRemarks())) {
                predicates.add(cb.like(root.get("remarks"), "%"+dto.getRemarks()+"%"));
            }
            if(dto.getDtuId()!=null && dto.getDtuId()!=0) {
            predicates.add(cb.equal(join.get("id"), dto.getDtuId()));
            }
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        //截取第一个字符，为-是倒序，为+正排序,后面为字段名称
        Sort.Direction direction = dto.getSort().substring(0, 1).equals("+") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, dto.getSort().substring(1));
        Page<InstructionDefinition> all = instructionDefinitionRepository.findAll(sp, PageRequest.of(dto.getPage(), dto.getLimit(), sort));
        System.out.println(all);
        return all;
    }

    @Override
    public InstructionDefinition findByDtuInfoAndInstructionType(DtuInfo dtuInfo, InstructionTypeEnum typeEnum) {
        return instructionDefinitionRepository.findByDtuInfoAndInstructionType(dtuInfo,typeEnum);
    }

    @Override
    public List<InstructionDefinition> findAllByDtuId(Long id) {
        DtuInfo dtuInfo = dtuInfoService.findById(id);
        return instructionDefinitionRepository.findAllByDtuInfo(dtuInfo);
    }

    // 格式化成字符串后再存放进缓存
    @Override
    public List<InstructionDefinition> findAllByDtuInfo(DtuInfo dtuInfo) {
        Object o = redisTemplate.opsForValue().get(Constant.INSTRUCTION_DEFINITION_CACHE_KEY + "::" + dtuInfo.getId());
        List<InstructionDefinition> instructionDefinitions = new ArrayList<>();
        try {
            if (o != null) {
                instructionDefinitions = objectMapper.readValue(o.toString(), new TypeReference<List<InstructionDefinition>>() {
                });
            } else {
                instructionDefinitions = instructionDefinitionRepository.findAllByDtuInfo(dtuInfo);
                String value = objectMapper.writeValueAsString(instructionDefinitions);
                redisTemplate.opsForValue().set(Constant.INSTRUCTION_DEFINITION_CACHE_KEY + "::" + dtuInfo.getId(), value);
            }
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return instructionDefinitions;
    }

    @Override
    public Result sendManually(DendManuallyDto dto) {
        DtuInfo dtuInfo = dtuInfoService.findById(dto.getDtuId());
        //先改为手动
        dtuInfo.setAutomaticAdjustment(false);
        dtuInfoService.update(dtuInfo);
        List<InstructionDefinition> instructionDefinitions = instructionDefinitionRepository.findAllByDtuInfo(dtuInfo);
        Optional<InstructionDefinition> first = instructionDefinitions.stream().filter(instructionDefinition -> instructionDefinition.getInstructionType() == dto.getType()).findFirst();
        if(first.isPresent()){
            return NettyServiceCommon.sendRelayCommandAccordingToLayIds(first.get());
        }
        return Result.error(500,"查不到指令！");
    }

    /**
     * 取相反指令——根据指令枚举类型来做的
     * @param instructionDefinition
     * @return
     */
    @Override
    public InstructionDefinition findContrary(InstructionDefinition instructionDefinition) {
        int ordinal = instructionDefinition.getInstructionType().ordinal();
        if(ordinal%2==0){
            ordinal=ordinal+1;
        }else {
            ordinal=ordinal-1;
        }
        return instructionDefinitionRepository.findAllByDtuInfoAndInstructionType(instructionDefinition.getDtuInfo(),InstructionTypeEnum.values()[ordinal]);
    }

    @Override
    public List<InstructionDefinition> findAll(InstructionDefinitionAllDto dto) {
        Specification<InstructionDefinition> spec= (root, query, cb)-> {
            List<Predicate> predicates = new ArrayList<>();
            if(dto.getId()!=null && dto.getId()!=0) {
                predicates.add(cb.equal(root.get("Id"), dto.getId()));
            }
            if(StringUtils.isNotBlank(dto.getInstructionType())) {
                predicates.add(cb.like(root.get("InstructionType"), "%"+dto.getInstructionType()+"%"));
            }
            if(StringUtils.isNotBlank(dto.getName())) {
                predicates.add(cb.like(root.get("Name"), "%"+dto.getName()+"%"));
            }
            if(StringUtils.isNotBlank(dto.getRemarks())) {
                predicates.add(cb.like(root.get("Remarks"), "%"+dto.getRemarks()+"%"));
            }
            if(dto.getDtuId()!=null && dto.getDtuId()!=0) {
                predicates.add(cb.equal(root.get("DtuId"), dto.getDtuId()));
            }
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        List<InstructionDefinition> all = instructionDefinitionRepository.findAll(spec);
        return all;
    }
}
