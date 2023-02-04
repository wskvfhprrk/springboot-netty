package com.hejz.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
@ApiModel
public class SensorDataCreateDto {
    @ApiModelProperty(value = "接收到数据的单位",required = true)
    @NotEmpty
    private java.util.Date createDate;
    @ApiModelProperty(value = "接收到数据",required = true)
    @NotEmpty
    private String data;
    @ApiModelProperty(value = "dtuId",required = true)
    @NotEmpty
    private Long dtuId;
    @ApiModelProperty(value = "接收到数据的names",required = true)
    @NotEmpty
    private String names;
    @ApiModelProperty(value = "接收到数据的单位",required = true)
    @NotEmpty
    private String units;
}
