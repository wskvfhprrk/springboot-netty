package com.hejz.dto;

import com.hejz.common.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-31 20:16
 * @Description:
 */
@Data
public class CommandStatusFindByPageDto extends Page {
//    @ApiModelProperty(value = "ID")
//    private Long id;
    @ApiModelProperty(value = "dtuId")
    private Long dtuId;
    @ApiModelProperty(value = "继电器命令ID")
    private Long commonId;
    @ApiModelProperty(value = "创建时间")
    private Date createDate;
    @ApiModelProperty(value = "修改时间")
    private Date updateDate;
    @ApiModelProperty(value = "当前状态——true是新的状态，false是过期的状态")
    private Boolean status;
}
