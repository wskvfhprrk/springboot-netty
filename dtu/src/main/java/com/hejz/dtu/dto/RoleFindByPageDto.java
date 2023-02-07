package com.hejz.dtu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.hejz.dtu.common.Page;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class RoleFindByPageDto extends Page {
    @ApiModelProperty(value = "名称")
    private String name;
}
