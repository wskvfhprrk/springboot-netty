package com.hejz.studay.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

/**
 * 更新入参——必须带主键
 */
@Data
public class RoleUpdateDto {
    @ApiModelProperty(value = "id",required = true,example = "1")
    @NotEmpty
    private Integer id;
    @ApiModelProperty(value = "名称")
    private String name;
}
