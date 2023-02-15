package com.hejz.dtu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.hejz.dtu.common.Page;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class UserFindByPageDto extends Page {
    @ApiModelProperty(value = "年龄")
    private Integer age;
    @ApiModelProperty(value = "用户名")
    private String username;
}
