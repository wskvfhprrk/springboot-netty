package com.hejz.dtu.dto;

import com.hejz.dtu.enm.InstructionTypeEnum;
import lombok.Data;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-02-15 00:15
 * @Description: 手动发送指令入参
 */
@Data
public class DendManuallyDto {
    private Long dtuId;
    private InstructionTypeEnum type;
}
