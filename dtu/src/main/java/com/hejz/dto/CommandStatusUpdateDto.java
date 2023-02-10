package com.hejz.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

/**
 * 更新入参——必须带主键
 */
@Data
public class CommandStatusUpdateDto {
    @ApiModelProperty(value = "ID",required = true)
    @NotEmpty
    private Long id;
    @ApiModelProperty(value = "继电器命令ID",required = true)
    @NotEmpty
    private Long commonId;
    @ApiModelProperty(value = "创建时间",required = true)
    @NotEmpty
    private java.util.Date createDate;
    @ApiModelProperty(value = "dtuId",required = true)
    @NotEmpty
    private Long dtuId;
    @ApiModelProperty(value = "当前状态——true是新的状态，false是过期的状态",required = true,example = "1")
    @NotEmpty
    private Integer status;
    @ApiModelProperty(value = "修改时间",required = true)
    @NotEmpty
    private java.util.Date updateDate;
}