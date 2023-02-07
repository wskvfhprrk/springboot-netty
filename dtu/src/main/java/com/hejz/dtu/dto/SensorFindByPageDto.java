package com.hejz.dtu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.hejz.dtu.common.Page;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class SensorFindByPageDto extends Page {
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
