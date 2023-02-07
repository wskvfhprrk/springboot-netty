package com.hejz.dtu.service.impl;

import com.hejz.dtu.dto.CommandStatusFindByPageDto;
import com.hejz.dtu.entity.CommandStatus;
import com.hejz.dtu.repository.CommandStatusRepository;
import com.hejz.dtu.service.CommandStatusService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommandStatusServiceImpl implements CommandStatusService {

    @Autowired
    private CommandStatusRepository commandStatusRepository;

    @Override
    public CommandStatus Save(CommandStatus commandStatus) {
        return commandStatusRepository.save(commandStatus);
    }

    @Override
    public void delete(Long id) {
        commandStatusRepository.deleteById( id);
    }

    @Override
    public CommandStatus findById(Long id) {
       return commandStatusRepository.findById( id).orElse(null);
    }

    @Override
    public Page<CommandStatus> findPage(CommandStatusFindByPageDto dto) {
        Specification<CommandStatus> sp= (root, query, cb)-> {
            List<Predicate> predicates = new ArrayList<>();
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
            predicates.add(cb.equal(root.get("dtuId"), dto.getDtuId()));
            }
            if(dto.getInstructionDefinitionId()!=null && dto.getInstructionDefinitionId()!=0) {
            predicates.add(cb.equal(root.get("instructionDefinitionId"), dto.getInstructionDefinitionId()));
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
