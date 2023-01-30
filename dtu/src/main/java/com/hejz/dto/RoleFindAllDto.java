package com.hejz.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleFindAllDto {
    @ApiModelProperty(value = "名称")
    private String name;
}
