package com.hejz.dtu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.hejz.dtu.common.Page;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class CommandStatusFindByPageDto extends Page {
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createDate;
    @ApiModelProperty(value = "当前状态——true是新的状态，false是过期的状态")
    private Boolean status;
    @ApiModelProperty(value = "修改时间")
    private java.util.Date updateDate;
    @ApiModelProperty(value = "ID")
    private Long dtuId;
    @ApiModelProperty(value = "ID")
    private Long instructionDefinitionId;
}
