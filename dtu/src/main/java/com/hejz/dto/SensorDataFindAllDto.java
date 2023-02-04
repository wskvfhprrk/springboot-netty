package com.hejz.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class SensorDataFindAllDto {
    @ApiModelProperty(value = "接收到数据的单位")
    private java.util.Date createDate;
    @ApiModelProperty(value = "接收到数据")
    private String data;
    @ApiModelProperty(value = "dtuId")
    private Long dtuId;
    @ApiModelProperty(value = "接收到数据的names")
    private String names;
    @ApiModelProperty(value = "接收到数据的单位")
    private String units;
}
