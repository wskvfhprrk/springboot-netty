package com.hejz.dto;

import com.hejz.enm.InstructionTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-02-01 08:54
 * @Description: 手动指令
 */
@Data
public class ManualCommandDto {

    @ApiModelProperty(value = "ManualCommandDto manualCommandDto")
    @NotEmpty(message = "dtuId不能为空值")
    private Long dtuId;
    @ApiModelProperty(value = "指令类型枚举")
    @NotEmpty(message = "指令类型枚举不能为空值")
    private InstructionTypeEnum instructionTypeEnum;
}
