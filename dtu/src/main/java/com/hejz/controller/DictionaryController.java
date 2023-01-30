package com.hejz.controller;

import com.hejz.common.PageResult;
import com.hejz.common.Result;
import com.hejz.dto.DictionaryCreateDto;
import com.hejz.dto.DictionaryFindAllDto;
import com.hejz.dto.DictionaryFindByPageDto;
import com.hejz.dto.DictionaryUpdateDto;
import com.hejz.entity.Dictionary;
import com.hejz.service.DictionaryService;
import com.hejz.vo.DictionaryAllVo;
import com.hejz.vo.DictionaryFindByPageVo;
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
 * 数据字典控制器
 * author: hejz
 * data: 2022-5-9
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
        dictionary = dictionaryService.Save(dictionary);
        return Result.ok(dictionary);

    }
    @PutMapping
    @ApiOperation("修改数据字典")
    public Result updateDictionary(@Valid @RequestBody DictionaryUpdateDto dto){
        Dictionary dictionary=new Dictionary();
        BeanUtils.copyProperties(dto,dictionary);
        dictionary = dictionaryService.Save(dictionary);
        return Result.ok(dictionary);
    }
    @DeleteMapping
    @ApiOperation("删除数据字典")
    public Result DeleteDictionary(Long id){
        dictionaryService.delete(id);
        return Result.ok();
    }

    @GetMapping("fingPage")
    @ApiOperation("条件查询数据字典")
    public Result<PageResult<DictionaryFindByPageVo>> findBypage(@Valid DictionaryFindByPageDto dto){
        Dictionary dictionary=new Dictionary();
        BeanUtils.copyProperties(dto,dictionary);
        Page<Dictionary> dictionaryPage = dictionaryService.findPage(dictionary, dto.getPage(), dto.getPage());
        List<DictionaryFindByPageVo> list = dictionaryPage.getContent().stream().map(d -> {
            DictionaryFindByPageVo vo = new DictionaryFindByPageVo();
            BeanUtils.copyProperties(d,vo);
            return vo;
        }).collect(Collectors.toList());
        PageResult<DictionaryFindByPageVo> pages=new PageResult<>();
        pages.setPage(dto.getPage());
        pages.setLimit(dto.getLimit());
        pages.setTotal(dictionaryPage.getTotalElements());
        pages.setItems(list);
        return Result.ok(pages);
    }

    @GetMapping
    @ApiOperation("分布条件查询数据字典所有的数据")
    public Result<List<DictionaryAllVo>> findAll(@Valid DictionaryFindAllDto dto){
        Dictionary dictionary=new Dictionary();
        BeanUtils.copyProperties(dto,dictionary);
        List<Dictionary> dictionaries = dictionaryService.findAll(dictionary);
        List<DictionaryAllVo> list = dictionaries.stream().map(d -> {
            DictionaryAllVo vo = new DictionaryAllVo();
            BeanUtils.copyProperties(d,vo);
            return vo;
        }).collect(Collectors.toList());
        return Result.ok(list);
    }
}
