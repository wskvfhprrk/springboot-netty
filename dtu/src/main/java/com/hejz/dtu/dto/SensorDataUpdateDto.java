package com.hejz.dtu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

/**
 * 更新入参——必须带主键
 */
@Data
public class SensorDataUpdateDto {
    @ApiModelProperty(value = "ID",required = true)
    @NotEmpty
    private Long id;
    @ApiModelProperty(value = "接收时间",required = true)
    @NotEmpty
    private java.util.Date createDate;
    @ApiModelProperty(value = "接收到数据",required = true)
    @NotEmpty
    private String data;
    @ApiModelProperty(value = "接收到数据的names",required = true)
    @NotEmpty
    private String names;
    @ApiModelProperty(value = "接收到数据的单位",required = true)
    @NotEmpty
    private String units;
    @ApiModelProperty(value = "ID",required = true)
    @NotEmpty
    private Long dtuId;
}
