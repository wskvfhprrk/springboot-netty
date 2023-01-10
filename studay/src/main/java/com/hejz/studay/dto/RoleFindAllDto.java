package com.hejz.studay.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class RoleFindAllDto {
    @ApiModelProperty(value = "名称")
    private String name;
}
