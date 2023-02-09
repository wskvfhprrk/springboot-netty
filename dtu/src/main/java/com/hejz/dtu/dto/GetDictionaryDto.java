package com.hejz.dtu.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-02-09 17:21
 * @Description:
 */
@Data
public class GetDictionaryDto {
    @ApiModelProperty(value = "应用模块")
    private String appModule;
    @ApiModelProperty(value = "字典类型")
    private String type;
    @ApiModelProperty(value = "上一级ID")
    private Long parentId;
}
