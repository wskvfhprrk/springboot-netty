package com.hejz.vo;

import com.hejz.common.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleFindByPageVo extends Page {
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "名称")
    private String name;
}
