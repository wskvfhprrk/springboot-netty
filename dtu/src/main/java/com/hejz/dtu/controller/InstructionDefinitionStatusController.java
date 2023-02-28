package com.hejz.dtu.controller;

import com.hejz.dtu.common.PageResult;
import com.hejz.dtu.dto.*;
import com.hejz.dtu.entity.InstructionDefinitionStatus;
import com.hejz.dtu.service.InstructionDefinitionStatusService;
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
 * 继电器命令状态控制器
 * author: hejz
 * data: 2023-2-7
 */
@RestController
@RequestMapping("instructionDefinitionStatus")
@Api(tags="继电器命令状态")
public class InstructionDefinitionStatusController {

    @Autowired
    private InstructionDefinitionStatusService instructionDefinitionStatusService;

    @PostMapping()
    @ApiOperation("添加继电器命令状态")
    public Result createCommandStatus(@Valid @RequestBody CommandStatusCreateDto dto){
        InstructionDefinitionStatus instructionDefinitionStatus =new InstructionDefinitionStatus();
        BeanUtils.copyProperties(dto, instructionDefinitionStatus);
        instructionDefinitionStatus = instructionDefinitionStatusService.save(instructionDefinitionStatus);
        return Result.ok(instructionDefinitionStatus);

    }
    @PutMapping
    @ApiOperation("修改继电器命令状态")
    public Result updateCommandStatus(@Valid @RequestBody CommandStatusUpdateDto dto){
        InstructionDefinitionStatus instructionDefinitionStatus =new InstructionDefinitionStatus();
        BeanUtils.copyProperties(dto, instructionDefinitionStatus);
        instructionDefinitionStatus = instructionDefinitionStatusService.save(instructionDefinitionStatus);
        return Result.ok(instructionDefinitionStatus);
    }
    @DeleteMapping
    @ApiOperation("删除继电器命令状态")
    public Result DeleteCommandStatus(Long id){
        instructionDefinitionStatusService.delete(id);
        return Result.ok();
    }

    @GetMapping("findPage")
    @ApiOperation("条件查询继电器命令状态")
    public Result<PageResult<CommandStatusFindByPageVo>> findBypage( @Valid CommandStatusFindByPageDto dto){
        InstructionDefinitionStatus instructionDefinitionStatus =new InstructionDefinitionStatus();
        BeanUtils.copyProperties(dto, instructionDefinitionStatus);
        Page<InstructionDefinitionStatus> commandStatusPage = instructionDefinitionStatusService.findPage(dto);
        List<CommandStatusFindByPageVo> list = commandStatusPage.getContent().stream().map(d -> {
            CommandStatusFindByPageVo vo = new CommandStatusFindByPageVo();
            BeanUtils.copyProperties(d,vo);
            return vo;
        }).collect(Collectors.toList());
        PageResult<CommandStatusFindByPageVo> pages=new PageResult<>();
        pages.setPage(dto.getPage());
        pages.setLimit(dto.getLimit());
        pages.setTotalPage(commandStatusPage.getTotalPages());
        pages.setTotal(commandStatusPage.getTotalElements());
        pages.setItems(list);
        return Result.ok(pages);
    }

}
