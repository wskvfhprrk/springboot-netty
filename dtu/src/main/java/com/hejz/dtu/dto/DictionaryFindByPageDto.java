package com.hejz.dtu.dto;

import com.hejz.dtu.enm.DictionaryTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.hejz.dtu.common.Page;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;

@Data
public class DictionaryFindByPageDto extends Page {
    @ApiModelProperty(value = "应用模块")
    private String appModule;
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
    @ApiModelProperty(value = "描述说明")
    private String description;
    @ApiModelProperty(value = "是否已用")
    private Boolean isUse;
    @ApiModelProperty(value = "显示名")
    private String itemName;
    @ApiModelProperty(value = "存储值")
    private String itemValue;
    @ApiModelProperty(value = "排序号")
    private Integer sortId;
    @ApiModelProperty(value = "字典类型")
    private String type;
    @ApiModelProperty(value = "上一级ID")
    private Long parentId;
}
