package com.hejz.dtu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
@ApiModel
public class SensorCreateDto {
    @ApiModelProperty(value = "获取值参考最大值",example = "22")
    private Integer max;
    @ApiModelProperty(value = "获取值参考最小值",example = "22")
    private Integer min;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "ID",required = true)
    @NotEmpty
    private Long dtuId;
    @ApiModelProperty(value = "ID")
    private Long maxInstructionDefinitionId;
    @ApiModelProperty(value = "ID")
    private Long minInstructionDefinitionId;
}
