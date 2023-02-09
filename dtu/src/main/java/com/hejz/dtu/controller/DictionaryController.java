package com.hejz.dtu.controller;

import com.hejz.dtu.common.PageResult;
import com.hejz.dtu.dto.*;
import com.hejz.dtu.entity.Dictionary;
import com.hejz.dtu.service.DictionaryService;
import com.hejz.dtu.common.Result;
import com.hejz.dtu.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据字典控制器
 * author: hejz
 * data: 2023-2-9
 */
@RestController
@RequestMapping("dictionary")
@Api(tags="数据字典")
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;

    @PostMapping()
    @ApiOperation("添加数据字典")
    public Result createDictionary(@Valid @RequestBody DictionaryCreateDto dto){
        Dictionary dictionary=new Dictionary();
        BeanUtils.copyProperties(dto,dictionary);
        dictionary.setCreateTime(new Date());
        dictionary.setDictionary(dictionaryService.findById(dto.getParentId()));
        dictionaryService.save(dictionary);
        return Result.ok();

    }
    @PutMapping
    @ApiOperation("修改数据字典")
    public Result updateDictionary(@Valid @RequestBody DictionaryUpdateDto dto){
        Dictionary dictionary=new Dictionary();
        BeanUtils.copyProperties(dto,dictionary);
        dictionary.setDictionary(dictionaryService.findById(dto.getParentId()));
        dictionaryService.update(dictionary);
        return Result.ok();
    }
    @DeleteMapping
    @ApiOperation("删除数据字典")
    public Result DeleteDictionary(Long id){
        dictionaryService.delete(id);
        return Result.ok();
    }

    @GetMapping("findPage")
    @ApiOperation("条件查询数据字典")
    public Result<PageResult<DictionaryFindByPageVo>> findBypage( @Valid DictionaryFindByPageDto dto){
        Dictionary dictionary=new Dictionary();
        BeanUtils.copyProperties(dto,dictionary);
        Page<Dictionary> dictionaryPage = dictionaryService.findPage(dto);
        List<DictionaryFindByPageVo> list = dictionaryPage.getContent().stream().map(d -> {
            DictionaryFindByPageVo vo = new DictionaryFindByPageVo();
            BeanUtils.copyProperties(d,vo);
            vo.setParentId(d.getDictionary().getId());
            return vo;
        }).collect(Collectors.toList());
        PageResult<DictionaryFindByPageVo> pages=new PageResult<>();
        pages.setPage(dto.getPage());
        pages.setLimit(dto.getLimit());
        pages.setTotalPage(dictionaryPage.getTotalPages());
        pages.setTotal(dictionaryPage.getTotalElements());
        pages.setItems(list);
        return Result.ok(pages);
    }

    @GetMapping
    @ApiOperation("分布条件查询数据字典所有的数据")
    public Result<List<DictionaryAllVo>> findAll(DictionaryAllDto dto){
        List<Dictionary> dictionaries = dictionaryService.findAll(dto);
        List<DictionaryAllVo> list = dictionaries.stream().map(d -> {
            DictionaryAllVo vo = new DictionaryAllVo();
            BeanUtils.copyProperties(d,vo);
            return vo;
        }).collect(Collectors.toList());
        return Result.ok(list);
    }

}
