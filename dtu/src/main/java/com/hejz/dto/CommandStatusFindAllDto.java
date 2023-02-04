package com.hejz.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class CommandStatusFindAllDto {
    @ApiModelProperty(value = "继电器命令ID")
    private Long commonId;
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createDate;
    @ApiModelProperty(value = "dtuId")
    private Long dtuId;
    @ApiModelProperty(value = "当前状态——true是新的状态，false是过期的状态")
    private Integer status;
    @ApiModelProperty(value = "修改时间")
    private java.util.Date updateDate;
}
