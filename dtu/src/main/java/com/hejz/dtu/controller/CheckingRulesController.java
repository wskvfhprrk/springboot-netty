package com.hejz.dtu.controller;

import com.hejz.dtu.common.PageResult;
import com.hejz.dtu.dto.*;
import com.hejz.dtu.entity.CheckingRules;
import com.hejz.dtu.service.CheckingRulesService;
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
 * 数据校检规则控制器
 * author: hejz
 * data: 2023-2-7
 */
@RestController
@RequestMapping("checkingRules")
@Api(tags="数据校检规则")
public class CheckingRulesController {

    @Autowired
    private CheckingRulesService checkingRulesService;

    @PostMapping()
    @ApiOperation("添加数据校检规则")
    public Result createCheckingRules(@Valid @RequestBody CheckingRulesCreateDto dto){
        CheckingRules checkingRules=new CheckingRules();
        BeanUtils.copyProperties(dto,checkingRules);
        checkingRules = checkingRulesService.save(checkingRules);
        return Result.ok(checkingRules);

    }
    @PutMapping
    @ApiOperation("修改数据校检规则")
    public Result updateCheckingRules(@Valid @RequestBody CheckingRulesUpdateDto dto){
        CheckingRules checkingRules=new CheckingRules();
        BeanUtils.copyProperties(dto,checkingRules);
        checkingRules = checkingRulesService.update(checkingRules);
        return Result.ok(checkingRules);
    }
    @DeleteMapping
    @ApiOperation("删除数据校检规则")
    public Result DeleteCheckingRules(Integer id){
        checkingRulesService.delete(id);
        return Result.ok();
    }

    @GetMapping("findPage")
    @ApiOperation("条件查询数据校检规则")
    public Result<PageResult<CheckingRulesFindByPageVo>> findBypage( @Valid CheckingRulesFindByPageDto dto){
        CheckingRules checkingRules=new CheckingRules();
        BeanUtils.copyProperties(dto,checkingRules);
        Page<CheckingRules> checkingRulesPage = checkingRulesService.findPage(dto);
        List<CheckingRulesFindByPageVo> list = checkingRulesPage.getContent().stream().map(d -> {
            CheckingRulesFindByPageVo vo = new CheckingRulesFindByPageVo();
            BeanUtils.copyProperties(d,vo);
            return vo;
        }).collect(Collectors.toList());
        PageResult<CheckingRulesFindByPageVo> pages=new PageResult<>();
        pages.setPage(dto.getPage());
        pages.setLimit(dto.getLimit());
        pages.setTotalPage(checkingRulesPage.getTotalPages());
        pages.setTotal(checkingRulesPage.getTotalElements());
        pages.setItems(list);
        return Result.ok(pages);
    }

    @GetMapping("findAll")
    @ApiOperation("分布条件查询数据校检规则所有的数据")
    public Result<List<CheckingRulesAllVo>> findAll(CheckingRulesAllDto dto){
        List<CheckingRules> dictionaries = checkingRulesService.findAll(dto);
        List<CheckingRulesAllVo> list = dictionaries.stream().map(d -> {
            CheckingRulesAllVo vo = new CheckingRulesAllVo();
            BeanUtils.copyProperties(d,vo);
            return vo;
        }).collect(Collectors.toList());
        return Result.ok(list);
    }

}
