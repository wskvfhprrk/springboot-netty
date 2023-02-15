package com.hejz.dtu.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserUpdateVo {
    @ApiModelProperty(value = "")
    private Integer id;
    @ApiModelProperty(value = "年龄")
    private Integer age;
    @ApiModelProperty(value = "用户名")
    private String username;
}
