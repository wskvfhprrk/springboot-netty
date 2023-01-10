package com.hejz.studay.vo;

import io.swagger.annotations.ApiModelProperty;
import com.hejz.studay.common.Page;
import lombok.Data;

@Data
public class RoleFindByPageVo extends Page {
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "名称")
    private String name;
}
