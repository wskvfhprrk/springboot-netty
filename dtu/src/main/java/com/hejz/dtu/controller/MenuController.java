package com.hejz.dtu.controller;

import com.hejz.dtu.common.PageResult;
import com.hejz.dtu.dto.*;
import com.hejz.dtu.entity.Menu;
import com.hejz.dtu.service.MenuService;
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
 * 菜单控制器
 * author: hejz
 * data: 2023-2-7
 */
@RestController
@RequestMapping("menu")
@Api(tags="菜单")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @PostMapping()
    @ApiOperation("添加菜单")
    public Result createMenu(@Valid @RequestBody MenuCreateDto dto){
        Menu menu=new Menu();
        BeanUtils.copyProperties(dto,menu);
        menu = menuService.save(menu);
        return Result.ok(menu);

    }
    @PutMapping
    @ApiOperation("修改菜单")
    public Result updateMenu(@Valid @RequestBody MenuUpdateDto dto){
        Menu menu=new Menu();
        BeanUtils.copyProperties(dto,menu);
        menu = menuService.update(menu);
        return Result.ok(menu);
    }
    @DeleteMapping
    @ApiOperation("删除菜单")
    public Result DeleteMenu(Integer id){
        menuService.delete(id);
        return Result.ok();
    }

    @GetMapping("findPage")
    @ApiOperation("条件查询菜单")
    public Result<PageResult<MenuFindByPageVo>> findBypage( @Valid MenuFindByPageDto dto){
        Menu menu=new Menu();
        BeanUtils.copyProperties(dto,menu);
        Page<Menu> menuPage = menuService.findPage(dto);
        List<MenuFindByPageVo> list = menuPage.getContent().stream().map(d -> {
            MenuFindByPageVo vo = new MenuFindByPageVo();
            BeanUtils.copyProperties(d,vo);
            return vo;
        }).collect(Collectors.toList());
        PageResult<MenuFindByPageVo> pages=new PageResult<>();
        pages.setPage(dto.getPage());
        pages.setLimit(dto.getLimit());
        pages.setTotalPage(menuPage.getTotalPages());
        pages.setTotal(menuPage.getTotalElements());
        pages.setItems(list);
        return Result.ok(pages);
    }

}
