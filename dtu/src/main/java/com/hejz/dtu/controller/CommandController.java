package com.hejz.dtu.controller;

import com.hejz.dtu.common.PageResult;
import com.hejz.dtu.dto.*;
import com.hejz.dtu.entity.Command;
import com.hejz.dtu.service.CommandService;
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
 * 指令控制器
 * author: hejz
 * data: 2023-2-7
 */
@RestController
@RequestMapping("command")
@Api(tags="指令")
public class CommandController {

    @Autowired
    private CommandService commandService;

    @PostMapping()
    @ApiOperation("添加指令")
    public Result createCommand(@Valid @RequestBody CommandCreateDto dto){
        Command command=new Command();
        BeanUtils.copyProperties(dto,command);
        command = commandService.save(command);
        return Result.ok(command);

    }
    @PutMapping
    @ApiOperation("修改指令")
    public Result updateCommand(@Valid @RequestBody CommandUpdateDto dto){
        Command command=new Command();
        BeanUtils.copyProperties(dto,command);
        command = commandService.update(command);
        return Result.ok(command);
    }
    @DeleteMapping
    @ApiOperation("删除指令")
    public Result DeleteCommand(Long id){
        commandService.delete(id);
        return Result.ok();
    }

    @GetMapping("findPage")
    @ApiOperation("条件查询指令")
    public Result<PageResult<CommandFindByPageVo>> findBypage( @Valid CommandFindByPageDto dto){
        Command command=new Command();
        BeanUtils.copyProperties(dto,command);
        Page<Command> commandPage = commandService.findPage(dto);
        List<CommandFindByPageVo> list = commandPage.getContent().stream().map(d -> {
            CommandFindByPageVo vo = new CommandFindByPageVo();
            BeanUtils.copyProperties(d,vo);
            vo.setCheckingRulesId(d.getCheckingRules().getId());
            vo.setNextLevelInstructionId(d.getNextLevelInstructionId());
            vo.setWaitTimeNextCommand(d.getWaitTimeNextCommand());
            return vo;
        }).collect(Collectors.toList());
        PageResult<CommandFindByPageVo> pages=new PageResult<>();
        pages.setPage(dto.getPage());
        pages.setLimit(dto.getLimit());
        pages.setTotalPage(commandPage.getTotalPages());
        pages.setTotal(commandPage.getTotalElements());
        pages.setItems(list);
        return Result.ok(pages);
    }

}
