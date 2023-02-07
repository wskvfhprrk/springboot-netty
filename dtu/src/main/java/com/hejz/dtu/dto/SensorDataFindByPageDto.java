package com.hejz.dtu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.hejz.dtu.common.Page;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class SensorDataFindByPageDto extends Page {
    @ApiModelProperty(value = "接收时间")
    private java.util.Date createDate;
    @ApiModelProperty(value = "接收到数据")
    private String data;
    @ApiModelProperty(value = "接收到数据的names")
    private String names;
    @ApiModelProperty(value = "接收到数据的单位")
    private String units;
    @ApiModelProperty(value = "ID")
    private Long dtuId;
}
