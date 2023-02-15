package com.hejz.dtu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

/**
 * 更新入参——必须带主键
 */
@Data
public class MenuUpdateDto {
    @ApiModelProperty(value = "菜单ID",required = true,example = "1")
    @NotEmpty
    private Integer id;
    @ApiModelProperty(value = "是否隐藏——true隐藏，false不隐藏（默认）",required = true)
    @NotEmpty
    private Boolean hidden;
    @ApiModelProperty(value = "图标",required = true)
    @NotEmpty
    private String icon;
    @ApiModelProperty(value = "路由器的名字",required = true)
    @NotEmpty
    private String name;
    @ApiModelProperty(value = "排序编号",required = true,example = "1")
    @NotEmpty
    private Integer orderByNo;
    @ApiModelProperty(value = "父级Id",required = true,example = "1")
    @NotEmpty
    private Integer parentId;
    @ApiModelProperty(value = "路径",required = true)
    @NotEmpty
    private String path;
    @ApiModelProperty(value = "标题",required = true)
    @NotEmpty
    private String title;
    @ApiModelProperty(value = "类型——1父级菜单、2子菜单、3按纽",required = true,example = "1")
    @NotEmpty
    private Integer type;
    @ApiModelProperty(value = "连接",required = true)
    @NotEmpty
    private String url;
}
