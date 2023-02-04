package com.hejz.controller;

import com.hejz.common.Result;
import com.hejz.common.PageResult;
import com.hejz.dto.*;
import com.hejz.entity.CommandStatus;
import com.hejz.service.CommandStatusService;
import com.hejz.service.DtuInfoService;
import com.hejz.vo.*;
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
 * data: 2023-2-4
 */
@RestController
@RequestMapping("commandStatus")
@Api(tags="继电器命令状态")
public class CommandStatusController {

    @Autowired
    private CommandStatusService commandStatusService;
    @Autowired
    private DtuInfoService dtuInfoService;

    @PostMapping()
    @ApiOperation("添加继电器命令状态")
    public Result createCommandStatus(@Valid @RequestBody CommandStatusCreateDto dto){
        CommandStatus commandStatus=new CommandStatus();
        BeanUtils.copyProperties(dto,commandStatus);
        commandStatus = commandStatusService.save(commandStatus);
        return Result.ok(commandStatus);

    }
    @PutMapping
    @ApiOperation("修改继电器命令状态")
    public Result updateCommandStatus(@Valid @RequestBody CommandStatusUpdateDto dto){
        CommandStatus commandStatus=new CommandStatus();
        BeanUtils.copyProperties(dto,commandStatus);
        commandStatus = commandStatusService.update(commandStatus);
        return Result.ok(commandStatus);
    }
    @DeleteMapping
    @ApiOperation("删除继电器命令状态")
    public Result DeleteCommandStatus(Long id){
        commandStatusService.delete(id);
        return Result.ok();
    }

    @GetMapping("findPage")
    @ApiOperation("条件查询继电器命令状态")
    public Result<PageResult<CommandStatusFindByPageVo>> findBypage( @Valid CommandStatusFindByPageDto dto){
        CommandStatus commandStatus=new CommandStatus();
        BeanUtils.copyProperties(dto,commandStatus);
        Page<CommandStatus> commandStatusPage = commandStatusService.findPage(dto);
        List<CommandStatusFindByPageVo> list = commandStatusPage.getContent().stream().map(d -> {
            CommandStatusFindByPageVo vo = new CommandStatusFindByPageVo();
            BeanUtils.copyProperties(d,vo);
            return vo;
        }).collect(Collectors.toList());
        PageResult<CommandStatusFindByPageVo> pages=new PageResult<>();
        pages.setPage(dto.getPage());
        pages.setLimit(dto.getLimit());
        pages.setTotal(commandStatusPage.getTotalElements());
        pages.setItems(list);
        return Result.ok(pages);
    }


//    @GetMapping
//    @ApiOperation("分布条件查询继电器命令状态所有的数据")
//    public Result<List<CommandStatusAllVo>> findAll(@Valid CommandStatusFindAllDto dto){
//        CommandStatus commandStatus=new CommandStatus();
//        BeanUtils.copyProperties(dto,commandStatus);
//        List<CommandStatus> dictionaries = commandStatusService.findAll(commandStatus);
//        List<CommandStatusAllVo> list = dictionaries.stream().map(d -> {
//            CommandStatusAllVo vo = new CommandStatusAllVo();
//            BeanUtils.copyProperties(d,vo);
//            return vo;
//        }).collect(Collectors.toList());
//        return Result.ok(list);
//    }
}
