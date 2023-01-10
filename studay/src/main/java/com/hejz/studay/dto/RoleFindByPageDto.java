package com.hejz.studay.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.hejz.studay.common.Page;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class RoleFindByPageDto extends Page {
    @ApiModelProperty(value = "名称")
    private String name;
}
