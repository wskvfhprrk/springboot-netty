package com.hejz.controller;

import com.hejz.common.PageResult;
import com.hejz.common.Result;
import com.hejz.dto.CommandStatusFindByPageDto;
import com.hejz.entity.CommandStatus;
import com.hejz.entity.CommandStatus;
import com.hejz.service.CommandStatusService;
import com.hejz.vo.CommandStatusFindByPageVo;
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
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 07:44
 * @Description: 命令状态
 */
@RestController
@RequestMapping("commandStatus")
@Api(tags ="命令状态")
public class CommandStatusController {

    @Autowired
    private CommandStatusService commandStatusService;

    @ApiOperation("根据dtuId查询所有感器信息")
    @GetMapping("all/{dtuId}")
    public List<CommandStatus> findAllByDtuId(@PathVariable Long dtuId) {
        return commandStatusService.findAllByDtuId(dtuId);
    }

    @ApiOperation("根据id查询感器信息")
    @GetMapping("{id}")
    public CommandStatus getCommandStatusById(@PathVariable("id") Long id) {
        return commandStatusService.findById(id);
    }

    @ApiOperation("添加感器信息")
    @PostMapping
    public CommandStatus save(@RequestBody CommandStatus commandStatus) {
        return commandStatusService.save(commandStatus);
    }

    @ApiOperation("更新继电器信息")
    @PutMapping
    public CommandStatus update(@RequestBody CommandStatus commandStatus) {
        return commandStatusService.update(commandStatus);
    }

    @ApiOperation("根据id删除感器信息")
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        commandStatusService.delete(id);
    }

    @ApiOperation("分页条件查询")
    @GetMapping("page")
    public Result<PageResult<CommandStatusFindByPageVo>> findBypage(@Valid CommandStatusFindByPageDto dto){
        Page<CommandStatus> commandStatusPage = commandStatusService.findPage(dto);
        List<CommandStatusFindByPageVo> list = commandStatusPage.getContent().stream().map(d -> {
            CommandStatusFindByPageVo vo = new CommandStatusFindByPageVo();
            BeanUtils.copyProperties(d,vo);
            return vo;
        }).collect(Collectors.toList());
        PageResult<CommandStatusFindByPageVo> pages=new PageResult<>();
        pages.setTotal(commandStatusPage.getTotalElements());
        pages.setPage(dto.getPage());
        pages.setLimit(dto.getLimit());
        pages.setItems(list);
        return Result.ok(pages);
    }

}
