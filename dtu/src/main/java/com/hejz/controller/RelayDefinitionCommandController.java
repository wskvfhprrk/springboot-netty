package com.hejz.controller;

import com.hejz.common.PageResult;
import com.hejz.common.Result;
import com.hejz.dto.RelayDefinitionCommandFindByPageDto;
import com.hejz.entity.InstructionDefinition;
import com.hejz.service.RelayDefinitionCommandService;
import com.hejz.vo.RelayDefinitionCommandFindByPageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 07:44
 * @Description: 继电器定义命令参数控制器
 */
@RestController
@RequestMapping("relayDefinitionCommand")
@Api(tags ="继电器定义命令参数控制器")
public class RelayDefinitionCommandController {

    @Autowired
    private RelayDefinitionCommandService relayDefinitionCommandService;

    @ApiOperation("根据dtuId查询所有继电器定义命令信息")
    @GetMapping("all/{dtuId}")
    public List<InstructionDefinition> findByAllDtuId(@PathVariable Long dtuId) {
        return relayDefinitionCommandService.findByAllDtuId(dtuId);
    }

    @ApiOperation("根据id查询感器信息")
    @GetMapping("{id}")
    public InstructionDefinition getRelayDefinitionCommandById(@PathVariable("id") Long id) {
        return relayDefinitionCommandService.findById(id);
    }

    @ApiOperation("添加感器信息")
    @PostMapping
    public InstructionDefinition save(@RequestBody InstructionDefinition instructionDefinition) {
        return relayDefinitionCommandService.save(instructionDefinition);
    }

    @ApiOperation("更新继电器定义命令信息")
    @PutMapping
    public InstructionDefinition update(@RequestBody InstructionDefinition instructionDefinition) {
        return relayDefinitionCommandService.update(instructionDefinition);
    }

    @ApiOperation("根据id删除感器信息")
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        relayDefinitionCommandService.delete(id);
    }

    @ApiOperation("根据dtuId删除所有感器信息")
    @DeleteMapping("deleteAllByDtuId/{dtuId}")
    @Transactional
    public void deleteAllByDtuId(@PathVariable("dtuId") Long dtuId) {
        relayDefinitionCommandService.deleteAllByDtuId(dtuId);
    }
    @GetMapping("findPage")
    @ApiOperation("条件查询用户信息")
    public Result<PageResult<RelayDefinitionCommandFindByPageVo>> findBypage(@Valid RelayDefinitionCommandFindByPageDto dto){
        InstructionDefinition instructionDefinition =new InstructionDefinition();
        BeanUtils.copyProperties(dto, instructionDefinition);
        Page<InstructionDefinition> relayDefinitionCommandPage = relayDefinitionCommandService.findPage(dto);
        List<RelayDefinitionCommandFindByPageVo> list = relayDefinitionCommandPage.getContent().stream().map(d -> {
            RelayDefinitionCommandFindByPageVo vo = new RelayDefinitionCommandFindByPageVo();
            BeanUtils.copyProperties(d,vo);
            return vo;
        }).collect(Collectors.toList());
        PageResult<RelayDefinitionCommandFindByPageVo> pages=new PageResult<>();
        pages.setTotal(relayDefinitionCommandPage.getTotalElements());
        pages.setPage(dto.getPage());
        pages.setLimit(dto.getLimit());
        pages.setItems(list);
        return Result.ok(pages);
    }
}
