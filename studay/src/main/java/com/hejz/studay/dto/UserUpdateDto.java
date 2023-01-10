package com.hejz.studay.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

/**
 * 更新入参——必须带主键
 */
@Data
public class UserUpdateDto {
    @ApiModelProperty(value = "",required = true,example = "1")
    @NotEmpty
    private Integer id;
    @ApiModelProperty(value = "用户名",required = true)
    @NotEmpty
    private String username;
    @ApiModelProperty(value = "年龄",example = "1")
    private Integer age;
    @ApiModelProperty(value = "角色id",example = "1")
    private Integer roleId;
}
