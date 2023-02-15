package com.hejz.dtu.controller;

import com.hejz.dtu.common.PageResult;
import com.hejz.dtu.dto.*;
import com.hejz.dtu.entity.Role;
import com.hejz.dtu.service.RoleService;
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
 * 角色实体类控制器
 * author: hejz
 * data: 2023-2-7
 */
@RestController
@RequestMapping("role")
@Api(tags="角色实体类")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping()
    @ApiOperation("添加角色实体类")
    public Result createRole(@Valid @RequestBody RoleCreateDto dto){
        Role role=new Role();
        BeanUtils.copyProperties(dto,role);
        role = roleService.save(role);
        return Result.ok(role);

    }
    @PutMapping
    @ApiOperation("修改角色实体类")
    public Result updateRole(@Valid @RequestBody RoleUpdateDto dto){
        Role role=new Role();
        BeanUtils.copyProperties(dto,role);
        role = roleService.update(role);
        return Result.ok(role);
    }
    @DeleteMapping
    @ApiOperation("删除角色实体类")
    public Result DeleteRole(Integer id){
        roleService.delete(id);
        return Result.ok();
    }

    @GetMapping("findPage")
    @ApiOperation("条件查询角色实体类")
    public Result<PageResult<RoleFindByPageVo>> findBypage( @Valid RoleFindByPageDto dto){
        Role role=new Role();
        BeanUtils.copyProperties(dto,role);
        Page<Role> rolePage = roleService.findPage(dto);
        List<RoleFindByPageVo> list = rolePage.getContent().stream().map(d -> {
            RoleFindByPageVo vo = new RoleFindByPageVo();
            BeanUtils.copyProperties(d,vo);
            return vo;
        }).collect(Collectors.toList());
        PageResult<RoleFindByPageVo> pages=new PageResult<>();
        pages.setPage(dto.getPage());
        pages.setLimit(dto.getLimit());
        pages.setTotalPage(rolePage.getTotalPages());
        pages.setTotal(rolePage.getTotalElements());
        pages.setItems(list);
        return Result.ok(pages);
    }

}
