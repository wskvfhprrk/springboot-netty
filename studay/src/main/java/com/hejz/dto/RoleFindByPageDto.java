package com.hejz.dto;

import io.swagger.annotations.ApiModelProperty;
import com.hejz.common.Page;
import lombok.Data;

@Data
public class RoleFindByPageDto extends Page {
    @ApiModelProperty(value = "名称")
    private String name;
}
