package com.hejz.dtu.service.impl;

import com.hejz.dtu.dto.InstructionDefinitionFindByPageDto;
import com.hejz.dtu.enm.InstructionTypeEnum;
import com.hejz.dtu.entity.DtuInfo;
import com.hejz.dtu.entity.InstructionDefinition;
import com.hejz.dtu.repository.InstructionDefinitionRepository;
import com.hejz.dtu.service.DtuInfoService;
import com.hejz.dtu.service.InstructionDefinitionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class InstructionDefinitionServiceImpl implements InstructionDefinitionService {

    @Autowired
    private InstructionDefinitionRepository instructionDefinitionRepository;
    @Autowired
    private DtuInfoService dtuInfoService;

    @Override
    public InstructionDefinition Save(InstructionDefinition instructionDefinition) {
        return instructionDefinitionRepository.save(instructionDefinition);
    }

    @Override
    public void delete(Long id) {
        instructionDefinitionRepository.deleteById( id);
    }

    @Override
    public InstructionDefinition findById(Long id) {
       return instructionDefinitionRepository.findById( id).orElse(null);
    }

    @Override
    public Page<InstructionDefinition> findPage(InstructionDefinitionFindByPageDto dto) {
        Specification<InstructionDefinition> sp= (root, query, cb)-> {
            List<Predicate> predicates = new ArrayList<>();
            if(dto.getInstructionType()!=null && dto.getInstructionType()!=0) {
            predicates.add(cb.equal(root.get("instructionType"), dto.getInstructionType()));
            }
            if(StringUtils.isNotBlank(dto.getName())) {
                predicates.add(cb.like(root.get("name"), "%"+dto.getName()+"%"));
            }
            if(StringUtils.isNotBlank(dto.getRemarks())) {
                predicates.add(cb.like(root.get("remarks"), "%"+dto.getRemarks()+"%"));
            }
            if(dto.getDtuId()!=null && dto.getDtuId()!=0) {
            predicates.add(cb.equal(root.get("dtuId"), dto.getDtuId()));
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

    @Override
    public List<InstructionDefinition> findAllByDtuInfo(DtuInfo dtuInfo) {
        return instructionDefinitionRepository.findAllByDtuInfo(dtuInfo);
    }
}
