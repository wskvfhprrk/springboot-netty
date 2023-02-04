package com.hejz.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommandStatusUpdateVo {
    @ApiModelProperty(value = "ID")
    private Long id;
    @ApiModelProperty(value = "继电器命令ID")
    private Long commonId;
    @ApiModelProperty(value = "创建时间")
    @com.fasterxml.jackson.annotation.JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private java.util.Date createDate;
    @ApiModelProperty(value = "dtuId")
    private Long dtuId;
    @ApiModelProperty(value = "当前状态——true是新的状态，false是过期的状态")
    private Integer status;
    @ApiModelProperty(value = "修改时间")
    @com.fasterxml.jackson.annotation.JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private java.util.Date updateDate;
}
