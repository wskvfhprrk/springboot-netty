package com.hejz.dtu.service.impl;

import com.hejz.dtu.dto.CommandAllDto;
import com.hejz.dtu.dto.CommandFindByPageDto;
import com.hejz.dtu.entity.CheckingRules;
import com.hejz.dtu.entity.Command;
import com.hejz.dtu.repository.CommandRepository;
import com.hejz.dtu.service.CommandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommandServiceImpl implements CommandService {

    @Autowired
    private CommandRepository commandRepository;

    @Override
    public Command save(Command command) {
        return commandRepository.save(command);
    }
    @Override
    public Command update(Command command) {
        return commandRepository.save(command);
    }

    @Override
    public void delete(Long id) {
        commandRepository.deleteById( id);
    }

    @Override
    public Command findById(Long id) {
       return commandRepository.findById( id).orElse(null);
    }

    @Override
    public Page<Command> findPage(CommandFindByPageDto dto) {
        Specification<Command> sp= (root, query, cb)-> {
            List<Predicate> predicates = new ArrayList<>();
            //关联查询
            Join<Command, CheckingRules> join =  root.join("checkingRules", JoinType.LEFT);
            if(StringUtils.isNotBlank(dto.getCalculationFormula())) {
                predicates.add(cb.like(root.get("calculationFormula"), "%"+dto.getCalculationFormula()+"%"));
            }
            if(dto.getCommandType()!=null && dto.getCommandType()!=0) {
            predicates.add(cb.equal(root.get("commandType"), dto.getCommandType()));
            }
            if(StringUtils.isNotBlank(dto.getInstructions())) {
                predicates.add(cb.like(root.get("instructions"), "%"+dto.getInstructions()+"%"));
            }
            if(dto.getIsUse()!=null) {
            predicates.add(cb.equal(root.get("isUse"), dto.getIsUse()));
            }
            if(StringUtils.isNotBlank(dto.getManufacturer())) {
                predicates.add(cb.like(root.get("manufacturer"), "%"+dto.getManufacturer()+"%"));
            }
            if(StringUtils.isNotBlank(dto.getName())) {
                predicates.add(cb.like(root.get("name"), "%"+dto.getName()+"%"));
            }
            if(StringUtils.isNotBlank(dto.getRemarks())) {
                predicates.add(cb.like(root.get("remarks"), "%"+dto.getRemarks()+"%"));
            }
            if(StringUtils.isNotBlank(dto.getUnit())) {
                predicates.add(cb.like(root.get("unit"), "%"+dto.getUnit()+"%"));
            }
            if(StringUtils.isNotBlank(dto.getWaitTimeNextCommand())) {
                predicates.add(cb.like(root.get("waitTimeNextCommand"), "%"+dto.getWaitTimeNextCommand()+"%"));
            }
            if(dto.getCheckingRulesId()!=null && dto.getCheckingRulesId()!=0) {
            predicates.add(cb.equal(join.get("id"), dto.getCheckingRulesId()));
            }
            if(dto.getNextLevelInstruction()!=null && dto.getNextLevelInstruction()!=0) {
            predicates.add(cb.equal(root.get("nextLevelInstruction"), dto.getNextLevelInstruction()));
            }
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        //截取第一个字符，为-是倒序，为+正排序,后面为字段名称
        Sort.Direction direction = dto.getSort().substring(0, 1).equals("+") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, dto.getSort().substring(1));
        Page<Command> all = commandRepository.findAll(sp, PageRequest.of(dto.getPage(), dto.getLimit(), sort));
        System.out.println(all);
        return all;
    }

    @Override
    public List<Command> findAll(CommandAllDto dto) {
        Specification<Command> spec= (root, query, cb)-> {
            List<Predicate> predicates = new ArrayList<>();
            if(dto.getId()!=null && dto.getId()!=0) {
                predicates.add(cb.equal(root.get("Id"), dto.getId()));
            }
            if(StringUtils.isNotBlank(dto.getCalculationFormula())) {
                predicates.add(cb.like(root.get("CalculationFormula"), "%"+dto.getCalculationFormula()+"%"));
            }
            if(StringUtils.isNotBlank(dto.getCommandType())) {
                predicates.add(cb.like(root.get("CommandType"), "%"+dto.getCommandType()+"%"));
            }
            if(StringUtils.isNotBlank(dto.getInstructions())) {
                predicates.add(cb.like(root.get("Instructions"), "%"+dto.getInstructions()+"%"));
            }
            if(dto.getIsUse()!= null ) {
                predicates.add(cb.equal(root.get("IsUse"), dto.getIsUse()));
            }
            if(StringUtils.isNotBlank(dto.getManufacturer())) {
                predicates.add(cb.like(root.get("Manufacturer"), "%"+dto.getManufacturer()+"%"));
            }
            if(StringUtils.isNotBlank(dto.getName())) {
                predicates.add(cb.like(root.get("Name"), "%"+dto.getName()+"%"));
            }
            if(dto.getNextLevelInstructionId()!=null && dto.getNextLevelInstructionId()!=0) {
                predicates.add(cb.equal(root.get("NextLevelInstructionId"), dto.getNextLevelInstructionId()));
            }
            if(StringUtils.isNotBlank(dto.getRemarks())) {
                predicates.add(cb.like(root.get("Remarks"), "%"+dto.getRemarks()+"%"));
            }
            if(dto.getWaitTimeNextCommand()!=null && dto.getWaitTimeNextCommand()!=0) {
                predicates.add(cb.equal(root.get("WaitTimeNextCommand"), dto.getWaitTimeNextCommand()));
            }
            if(dto.getCheckingRulesId()!=null && dto.getCheckingRulesId()!=0) {
                predicates.add(cb.equal(root.get("CheckingRulesId"), dto.getCheckingRulesId()));
            }
            Predicate[] andPredicate = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(andPredicate));
        };
        List<Command> all = commandRepository.findAll(spec);
        return all;
    }
}
