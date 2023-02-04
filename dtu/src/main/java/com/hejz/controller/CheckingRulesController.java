package com.hejz.controller;

import com.hejz.common.PageResult;
import com.hejz.common.Result;
import com.hejz.dto.CheckingRulesDto;
import com.hejz.dto.CheckingRulesFindByPageDto;
import com.hejz.dto.CheckingRulesUpdateDto;
import com.hejz.entity.CheckingRules;
import com.hejz.service.CheckingRulesService;
import com.hejz.vo.CheckingRulesFindByPageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 07:44
 * @Description: 校验规则
 */
@RestController
@RequestMapping("checkingRules")
@Api(tags ="校验规则")
public class CheckingRulesController {

    @Autowired
    private CheckingRulesService checkingRulesService;

    @ApiOperation("根据id查询感器信息")
    @GetMapping("{id}")
    public Result getCheckingRulesById(@PathVariable("id") Integer id){
        return Result.ok(checkingRulesService.findById(id));
    }
    @ApiOperation("添加感器信息")
    @PostMapping
    public Result save(@RequestBody CheckingRulesDto checkingRulesDto){
        return Result.ok(checkingRulesService.save(checkingRulesDto));
    }
    @ApiOperation("更新dtu信息")
    @PutMapping
    public Result update(@RequestBody CheckingRulesUpdateDto checkingRulesUpdateDto){
        return Result.ok(checkingRulesService.update(checkingRulesUpdateDto));
    }
    @ApiOperation("根据id删除感器信息")
    @DeleteMapping("{id}")
    public Result delete(@PathVariable("id") Integer id){
        checkingRulesService.delete(id);
        return Result.ok();
    }
    @ApiOperation("分页条件查询")
    @GetMapping("findPage")
    public Result<PageResult<CheckingRulesFindByPageVo>> findBypage( CheckingRulesFindByPageDto dto){
        Page<CheckingRules> checkingRulesPage = checkingRulesService.findPage(dto);
        List<CheckingRulesFindByPageVo> list = checkingRulesPage.getContent().stream().map(d -> {
            CheckingRulesFindByPageVo vo = new CheckingRulesFindByPageVo();
            BeanUtils.copyProperties(d,vo);
            return vo;
        }).collect(Collectors.toList());
        PageResult<CheckingRulesFindByPageVo> pages=new PageResult<>();
        pages.setTotal(checkingRulesPage.getTotalElements());
        pages.setPage(dto.getPage());
        pages.setLimit(dto.getLimit());
        pages.setItems(list);
        return Result.ok(pages);
    }
}
