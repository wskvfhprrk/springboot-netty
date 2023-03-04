package com.hejz.dtu.service.impl;

import com.hejz.dtu.dto.CommandStatusFindByPageDto;
import com.hejz.dtu.entity.DtuInfo;
import com.hejz.dtu.entity.InstructionDefinitionStatus;
import com.hejz.dtu.entity.InstructionDefinition;
import com.hejz.dtu.repository.CommandStatusRepository;
import com.hejz.dtu.service.InstructionDefinitionStatusService;
import com.hejz.dtu.service.InstructionDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class InstructionDefinitionStatusServiceImpl implements InstructionDefinitionStatusService {

    @Autowired
    private CommandStatusRepository commandStatusRepository;

    @Override
    public InstructionDefinitionStatus save(InstructionDefinitionStatus instructionDefinitionStatus) {
        return commandStatusRepository.save(instructionDefinitionStatus);
    }

    @Override
    public void delete(Long id) {
        commandStatusRepository.deleteById( id);
    }

    @Override
    public InstructionDefinitionStatus findById(Long id) {
       return commandStatusRepository.findById( id).orElse(null);
    }

    @Override
    public Page<InstructionDefinitionStatus> findPage(CommandStatusFindByPageDto dto) {
        Specification<InstructionDefinitionStatus> sp= (root, query, cb)-> {
            List<Predicate> predicates = new ArrayList<>();
            Join<InstructionDefinitionStatus, DtuInfo> join= root.join("dtuInfo", JoinType.LEFT);
            if(dto.getCreateDate()!=null) {
            predicates.add(cb.equal(root.get("createDate"), dto.getCreateDate()));
            }
            if(dto.getStatus()!=null) {
            predicates.add(cb.equal(root.get("status"), dto.getStatus()));
            }
            if(dto.getUpdateDate()!=null) {
            predicates.add(cb.equal(root.get("updateDate"), dto.getUpdateDate()));
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
        Page<InstructionDefinitionStatus> all = commandStatusRepository.findAll(sp, PageRequest.of(dto.getPage(), dto.getLimit(), sort));
        System.out.println(all);
        return all;
    }


    @Override
    public List<InstructionDefinitionStatus> findByInstructionDefinition(InstructionDefinition instructionDefinition) {
        //InstructionDefinition中含有对应的dtu信息，不需要再根据设备查询了
        return commandStatusRepository.findByInstructionDefinitionAndStatus(instructionDefinition,true);
    }

}
