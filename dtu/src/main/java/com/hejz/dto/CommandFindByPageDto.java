package com.hejz.dto;

import com.hejz.enm.CommandTypeEnum;
import com.hejz.entity.CheckingRules;
import com.hejz.entity.DtuInfo;
import lombok.Data;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-02-05 16:10
 * @Description:
 */
@Data
public class CommandFindByPageDto {
    private Long id;
    private String name;
    private DtuInfo dtuInfo;
    private String instructions;
    private String remarks;
    private CheckingRules checkingRules;
    private CommandTypeEnum commandType;
    private String calculationFormula = "D/10";
}
