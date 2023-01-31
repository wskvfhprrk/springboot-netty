package com.hejz.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-31 20:16
 * @Description:
 */
@Data
public class CommandStatusFindByPageVo {
    @ApiModelProperty(value = "ID")
    private Long id;
    @ApiModelProperty(value = "dtuId")
    private Long dtuId;
    @ApiModelProperty(value = "继电器命令ID")
    private Long commonId;
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private Date createDate;
    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private Date updateDate;
    @ApiModelProperty(value = "当前状态——true是新的状态，false是过期的状态")
    private Boolean status;
}
