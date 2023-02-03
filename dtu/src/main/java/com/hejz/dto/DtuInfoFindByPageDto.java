package com.hejz.dto;

import com.hejz.common.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-31 21:00
 * @Description:
 */
@Data
public class DtuInfoFindByPageDto extends Page {
    @ApiModelProperty(value = "imei")
    private String imei;
    @ApiModelProperty(value = "指令前是否带imei")
    private Boolean noImei = true;
}
