package com.hejz.dtu.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SensorUpdateVo {
    @ApiModelProperty(value = "ID")
    private Long id;
    @ApiModelProperty(value = "获取值参考最大值")
    private Integer max;
    @ApiModelProperty(value = "获取值参考最小值")
    private Integer min;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "ID")
    private Long dtuId;
    @ApiModelProperty(value = "ID")
    private Long maxInstructionDefinitionId;
    @ApiModelProperty(value = "ID")
    private Long minInstructionDefinitionId;
}
