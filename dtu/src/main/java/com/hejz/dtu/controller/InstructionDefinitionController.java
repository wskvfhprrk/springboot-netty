package com.hejz.dtu.controller;

import com.hejz.dtu.common.PageResult;
import com.hejz.dtu.dto.*;
import com.hejz.dtu.entity.InstructionDefinition;
import com.hejz.dtu.service.InstructionDefinitionService;
import com.hejz.dtu.common.Result;
import com.hejz.dtu.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 继电器定义指令控制器
 * author: hejz
 * data: 2023-2-7
 */
@RestController
@RequestMapping("instructionDefinition")
@Api(tags="继电器定义指令")
public class InstructionDefinitionController {

    @Autowired
    private InstructionDefinitionService instructionDefinitionService;

    @PostMapping()
    @ApiOperation("添加继电器定义指令")
    public Result createInstructionDefinition(@Valid @RequestBody InstructionDefinitionCreateDto dto){
        InstructionDefinition instructionDefinition=new InstructionDefinition();
        BeanUtils.copyProperties(dto,instructionDefinition);
        instructionDefinition = instructionDefinitionService.Save(instructionDefinition);
        return Result.ok(instructionDefinition);

    }
    @PutMapping
    @ApiOperation("修改继电器定义指令")
    public Result updateInstructionDefinition(@Valid @RequestBody InstructionDefinitionUpdateDto dto){
        InstructionDefinition instructionDefinition=new InstructionDefinition();
        BeanUtils.copyProperties(dto,instructionDefinition);
        instructionDefinition = instructionDefinitionService.Save(instructionDefinition);
        return Result.ok(instructionDefinition);
    }
    @DeleteMapping
    @ApiOperation("删除继电器定义指令")
    public Result DeleteInstructionDefinition(Long id){
        instructionDefinitionService.delete(id);
        return Result.ok();
    }

    @GetMapping("findPage")
    @ApiOperation("条件查询继电器定义指令")
    public Result<PageResult<InstructionDefinitionFindByPageVo>> findBypage( @Valid InstructionDefinitionFindByPageDto dto){
        InstructionDefinition instructionDefinition=new InstructionDefinition();
        BeanUtils.copyProperties(dto,instructionDefinition);
        Page<InstructionDefinition> instructionDefinitionPage = instructionDefinitionService.findPage(dto);
        List<InstructionDefinitionFindByPageVo> list = instructionDefinitionPage.getContent().stream().map(d -> {
            InstructionDefinitionFindByPageVo vo = new InstructionDefinitionFindByPageVo();
            BeanUtils.copyProperties(d,vo);
            return vo;
        }).collect(Collectors.toList());
        PageResult<InstructionDefinitionFindByPageVo> pages=new PageResult<>();
        pages.setPage(dto.getPage());
        pages.setLimit(dto.getLimit());
        pages.setTotalPage(instructionDefinitionPage.getTotalPages());
        pages.setTotal(instructionDefinitionPage.getTotalElements());
        pages.setItems(list);
        return Result.ok(pages);
    }

}
