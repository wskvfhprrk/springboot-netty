package com.hejz.controller;

import com.hejz.common.PageResult;
import com.hejz.common.Result;
import com.hejz.dto.RoleCreateDto;
import com.hejz.dto.RoleFindAllDto;
import com.hejz.dto.RoleFindByPageDto;
import com.hejz.dto.RoleUpdateDto;
import com.hejz.entity.Role;
import com.hejz.service.RoleService;
import com.hejz.vo.RoleAllVo;
import com.hejz.vo.RoleFindByPageVo;
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
 * 角色控制器
 * author: hejz
 * data: 2022-5-9
 */
@RestController
@RequestMapping("role")
@Api(tags="角色")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping()
    @ApiOperation("添加角色")
    public Result createRole(@Valid @RequestBody RoleCreateDto dto){
        Role role=new Role();
        BeanUtils.copyProperties(dto,role);
        role = roleService.Save(role);
        return Result.ok(role);

    }
    @PutMapping
    @ApiOperation("修改角色")
    public Result updateRole(@Valid @RequestBody RoleUpdateDto dto){
        Role role=new Role();
        BeanUtils.copyProperties(dto,role);
        role = roleService.Save(role);
        return Result.ok(role);
    }
    @DeleteMapping
    @ApiOperation("删除角色")
    public Result DeleteRole(Integer id){
        roleService.delete(id);
        return Result.ok();
    }

    @GetMapping("fingPage")
    @ApiOperation("条件查询角色")
    public Result<PageResult<RoleFindByPageVo>> findBypage(@Valid RoleFindByPageDto dto){
        Role role=new Role();
        BeanUtils.copyProperties(dto,role);
        Page<Role> rolePage = roleService.findPage(role, dto.getPage(), dto.getLimit());
        List<RoleFindByPageVo> list = rolePage.getContent().stream().map(d -> {
            RoleFindByPageVo vo = new RoleFindByPageVo();
            BeanUtils.copyProperties(d,vo);
            return vo;
        }).collect(Collectors.toList());
        PageResult<RoleFindByPageVo> pages=new PageResult<>();
        pages.setTotal(rolePage.getTotalElements());
        pages.setPage(dto.getPage());
        pages.setLimit(dto.getLimit());
        pages.setItems(list);
        return Result.ok(pages);
    }

    @GetMapping
    @ApiOperation("分布条件查询角色所有的数据")
    public Result<List<RoleAllVo>> findAll(@Valid RoleFindAllDto dto){
        Role role=new Role();
        BeanUtils.copyProperties(dto,role);
        List<Role> dictionaries = roleService.findAll(role);
        List<RoleAllVo> list = dictionaries.stream().map(d -> {
            RoleAllVo vo = new RoleAllVo();
            BeanUtils.copyProperties(d,vo);
            return vo;
        }).collect(Collectors.toList());
        return Result.ok(list);
    }
}
