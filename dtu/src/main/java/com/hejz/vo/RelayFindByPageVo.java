package com.hejz.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-31 21:23
 * @Description:
 */
@Data
public class RelayFindByPageVo {
    @ApiModelProperty(value = "ID")
    private Long id;
    @ApiModelProperty(value = "dtuId")
    private Long dtuId;
    @ApiModelProperty(value = "感应器编号地址——发出接收时指令地址位（每个感应器都有一个地址位的）")
    private Integer adrss;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "关联打开命令发出的指令")
    private String opneHex;
    @ApiModelProperty(value = "关联关闭命令发出的指令")
    private String closeHex;
    @ApiModelProperty(value = "关联发出的链接")
    private String url;
    @ApiModelProperty(value = "备注信息")
    private String remark;
}
