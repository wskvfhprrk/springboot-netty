package com.hejz.dtu.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;

/**
 * 更新入参——必须带主键
 */
@Data
public class DictionaryUpdateDto {
    @ApiModelProperty(value = "数据字典ID",required = true)
    @NotEmpty
    private Long id;
    @ApiModelProperty(value = "应用模块")
    private String appModule;
    @ApiModelProperty(value = "创建时间",required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @NotEmpty
    private java.util.Date createTime;
    @ApiModelProperty(value = "描述说明")
    private String description;
    @ApiModelProperty(value = "是否已用",required = true)
    @NotEmpty
    private Boolean isUse;
    @ApiModelProperty(value = "显示名",required = true)
    @NotEmpty
    private String itemName;
    @ApiModelProperty(value = "存储值")
    private String itemValue;
    @ApiModelProperty(value = "排序号",required = true,example = "1")
    @NotEmpty
    private Integer sortId;
    @ApiModelProperty(value = "字典类型",required = true,example = "1")
    @NotEmpty
    private String type;
    @ApiModelProperty(value = "上一级ID")
    private Long parentId;
}
