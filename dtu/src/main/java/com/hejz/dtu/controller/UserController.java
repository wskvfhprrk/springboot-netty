package com.hejz.dtu.controller;

import com.hejz.dtu.common.PageResult;
import com.hejz.dtu.common.Result;
import com.hejz.dtu.dto.*;
import com.hejz.dtu.entity.User;
import com.hejz.dtu.service.UserService;
import com.hejz.dtu.vo.UserFindAllVo;
import com.hejz.dtu.vo.UserFindByPageVo;
import com.hejz.dtu.vo.UserInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户信息控制器
 * author: hejz
 * data: 2022-5-9
 */
@RestController
@RequestMapping("user")
@Api(tags="用户信息")
public class UserController {

    @Autowired
    private UserService userService;

    String token = "admin-token";

    @ApiOperation("登陆")
    @PostMapping("login")
    public Result login(@RequestBody LoginUserDto loginUserDto, HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();
        map.put("\"token\"", token);
        //操作cookies
        Cookie cookie = new Cookie("Admin-Token",token);
        response.addCookie(cookie);
//        return Result.ok("'token':'admin-token");
        return Result.ok(map);
    }

    @ApiOperation("获取用户信息")
    @GetMapping("info")
    public Result<UserInfoVo> getUserInfo(String token) {
        return Result.ok(new UserInfoVo("zs", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif", "zs", Arrays.asList("admin")));
    }

    @ApiOperation("登陆")
    @PostMapping("logout")
    public Result logout() {
        return Result.ok();
    }

    @PostMapping()
    @ApiOperation("添加用户信息")
    public Result createUser(@Valid @RequestBody UserCreateDto dto){
        User user=new User();
        BeanUtils.copyProperties(dto,user);
        user = userService.save(user);
        return Result.ok(user);

    }
    @PutMapping
    @ApiOperation("修改用户信息")
    public Result updateUser(@Valid @RequestBody UserUpdateDto dto){
        User user=new User();
        BeanUtils.copyProperties(dto,user);
        user = userService.update(user);
        return Result.ok(user);
    }
    @DeleteMapping("/{id}")
    @ApiOperation("删除用户信息")
    public Result DeleteUser(@PathVariable Integer id){
        userService.delete(id);
        return Result.ok();
    }

    @GetMapping("findPage")
    @ApiOperation("条件分页查询用户信息")
    public Result<PageResult<UserFindByPageVo>> findBypage(@Valid UserFindByPageDto dto){
        Page<User> userPage = userService.findPage(dto);
        List<UserFindByPageVo> list = userPage.getContent().stream().map(d -> {
            UserFindByPageVo vo = new UserFindByPageVo();
            BeanUtils.copyProperties(d,vo);
            return vo;
        }).collect(Collectors.toList());
        PageResult<UserFindByPageVo> pages=new PageResult<>();
        pages.setTotal(userPage.getTotalElements());
        pages.setPage(dto.getPage());
        pages.setLimit(dto.getLimit());
        pages.setItems(list);
        return Result.ok(pages);
    }

    @GetMapping("findAll")
    @ApiOperation("条件查询用户信息")
    public Result<UserFindAllVo> findBypage(@Valid UserFindAllDto dto){
        List<User> userList = userService.findAll(dto);
        List<UserFindAllVo> list = userList.stream().map(d -> {
            UserFindAllVo vo = new UserFindAllVo();
            BeanUtils.copyProperties(d,vo);
            return vo;
        }).collect(Collectors.toList());
        return Result.ok(list);
    }

}
