package com.hejz.dtu.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hejz.dtu.enm.CommandTypeEnum;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * 指令实体类
 * author: hejz
 * data: 2023-2-7
 */
@Getter
@Setter
@Entity(name = "equ_command")
@org.hibernate.annotations.Table(appliesTo = "equ_command", comment = "指令")
@NoArgsConstructor
@AllArgsConstructor
public class Command implements Serializable{

    @Id
    @SequenceGenerator(
            name = "equ_command_sequence",
            sequenceName = "equ_command_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "equ_command_sequence"
    )
    @Column(
            name = "id",
            nullable = false,
            columnDefinition = "bigint" + " COMMENT '指令ID'"
    )
    private Long id;

    @Column(
            name = "calculation_formula",
            nullable = true,
            columnDefinition="varchar(30)"+" COMMENT '校正数据计算公式——D是测得数据，如果实际数据是原来的小10倍加上5，公式为D/10+5,用测得的结果带入计算公式得到最后实际结果'"
    )
    private String calculationFormula;

    @Column(
            name = "command_type",
            nullable = true,
            columnDefinition = "int" + " COMMENT '指令类型'"
    )
    private CommandTypeEnum commandType;

    @Column(
            name = "instructions",
            nullable = false,
            columnDefinition="varchar(100)"+" COMMENT '指令'"
    )
    private String instructions;

    @Column(
            name = "is_use",
            nullable = false,
            columnDefinition = "bit" + " COMMENT '是否正在使用——true正在使用，flase没有使用'"
    )
    private Boolean isUse;

    @Column(
            name = "manufacturer",
            nullable = false,
            columnDefinition="varchar(100)"+" COMMENT '生产厂商'"
    )
    private String manufacturer;

    @Column(
            name = "name",
            nullable = false,
            columnDefinition="varchar(100)"+" COMMENT '指令名称'"
    )
    private String name;

    @Column(
            name = "remarks",
            columnDefinition="varchar(255)"+" COMMENT '备注'"
    )
    private String remarks;



    @Column(
            name = "wait_time_next_command",
            nullable = true,
            columnDefinition = "int(5)" + " COMMENT '等待时间下一指令（单位：秒）'"
    )
    private Integer waitTimeNextCommand;

    /**
     * 外键表——checking_rules中的字段id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"command"})
    @JoinColumn(name = "checking_rules_id")
    private CheckingRules checkingRules;
    /**
     * 外键表——equ_command中的字段id
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"command"})
    @JoinColumn(name = "next_level_instruction", insertable = false, updatable = false)
    private Command nextLevelInstructionId;

//    @Column(
//            name = "next_level_instruction_id",
//            columnDefinition = "bigint" + " COMMENT '下一个指令ID'"
//    )
//    private Long nextLevelInstructionId;


    @JsonIgnoreProperties(value = {"commands"})
    @ManyToMany( mappedBy = "commands", fetch = FetchType.LAZY)
    private Set<InstructionDefinition> instructionDefinitions;

    public Command(String manufacturer, String name, String remarks, String instructions, CheckingRules checkingRules, CommandTypeEnum commandType, String calculationFormula,  Integer waitTimeNextCommand, Command nextLevelInstructionId, Boolean isUse) {
        this.calculationFormula = calculationFormula;
        this.commandType = commandType;
        this.instructions = instructions;
        this.isUse = isUse;
        this.manufacturer = manufacturer;
        this.name = name;
        this.remarks = remarks;
        this.waitTimeNextCommand = waitTimeNextCommand;
        this.checkingRules = checkingRules;
        this.nextLevelInstructionId = nextLevelInstructionId;
    }

}
