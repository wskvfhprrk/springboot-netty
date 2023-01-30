package com.hejz.dto;

import com.hejz.common.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleFindByPageDto extends Page {
    @ApiModelProperty(value = "名称")
    private String name;
}
