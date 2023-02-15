package com.hejz.dtu.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MenuAllVo {
    @ApiModelProperty(value = "菜单ID")
    private Integer id;
    @ApiModelProperty(value = "是否隐藏——true隐藏，false不隐藏（默认）")
    private Boolean hidden;
    @ApiModelProperty(value = "图标")
    private String icon;
    @ApiModelProperty(value = "路由器的名字")
    private String name;
    @ApiModelProperty(value = "排序编号")
    private Integer orderByNo;
    @ApiModelProperty(value = "父级Id")
    private Integer parentId;
    @ApiModelProperty(value = "路径")
    private String path;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "类型——1父级菜单、2子菜单、3按纽")
    private Integer type;
    @ApiModelProperty(value = "连接")
    private String url;
}
