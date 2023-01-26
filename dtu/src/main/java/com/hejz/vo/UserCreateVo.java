package com.hejz.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserCreateVo {
    @ApiModelProperty(value = "")
    private Integer id;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "年龄")
    private Integer age;
    @ApiModelProperty(value = "角色id")
    private Integer roleId;
}
