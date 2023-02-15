package com.hejz.dtu.dto;

import com.hejz.dtu.enm.DictionaryTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;


@Data
@ApiModel
public class DictionaryCreateDto {
    @ApiModelProperty(value = "应用模块")
    private String appModule;
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
    @ApiModelProperty(value = "排序号",required = true,example = "22")
    @NotEmpty
    private Integer sortId;
    @ApiModelProperty(value = "字典类型",required = true,example = "22")
    @NotEmpty
    @Enumerated(EnumType.STRING)
    private DictionaryTypeEnum type;
    @ApiModelProperty(value = "上一级ID")
    private Long parentId;
}
