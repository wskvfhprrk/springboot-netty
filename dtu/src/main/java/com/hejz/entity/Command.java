package com.hejz.entity;

import com.hejz.enm.CommandTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-02-05 13:39
 * @Description: 指令——感应器和继电器
 */
@Data
@Entity(name = "tb_command")
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(appliesTo = "tb_command", comment = "指令")
public class Command {
    @Id
    @SequenceGenerator(
            name = "command_sequence",
            sequenceName = "command_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "command_sequence"
    )
    @Column(
            name = "id",
            nullable = false,
            columnDefinition = "bigint" + " COMMENT 'ID'"
    )
    private Long id;
    //生产厂商
    @Column(
            name = "manufacturer",
            nullable = false,
            columnDefinition = "varchar(100)" + " COMMENT '生产厂商'"
    )
    private String manufacturer;
    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "varchar(100)" + " COMMENT '指令名称'"
    )
    private String name;

    //指令
    @Column(
            name = "instructions",
            nullable = false,
            columnDefinition = "varchar(100)" + " COMMENT '指令'"
    )
    private String instructions;
    @Column(
            name = "remarks",
            nullable = true,
            columnDefinition = "varchar(255)" + " COMMENT '备注'"
    )
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "checking_rules_id", nullable = true)
    private CheckingRules checkingRules;
    @Column(
            name = "command_type",
            nullable = true,
            columnDefinition = "int(2)" + " COMMENT '指令类型'"
    )
    private CommandTypeEnum commandType;
    /**
     * 校正数据计算公式——D是测得数据，如果实际数据是原来的小10倍加上5，公式为D/10+5,用测得的结果带入计算公式得到最后实际结果
     */
    @Column(
            name = "calculation_formula",
            nullable = true,
            columnDefinition="varchar(30) default 'D/10'"+" COMMENT '校正数据计算公式——D是测得数据，如果实际数据是原来的小10倍加上5，公式为D/10+5,用测得的结果带入计算公式得到最后实际结果'"
    )
    private String calculationFormula = "D/10";
    /**
     * 接收到数据的单位
     */
    @Column(
            name = "unit",
            nullable = true,
            columnDefinition="varchar(15)"+" COMMENT '接收到数据的单位'"
    )
    private String unit;
    //是否正在使用
    @Column(
            name = "is_use",
            nullable = false,
            columnDefinition="bit"+" COMMENT '是否正在使用——true正在使用，flase没有使用'"
    )
    private Boolean isUse=false;
    //等待时间下一指令
    @Column(
            name = "wait_time_next_command",
            columnDefinition="varchar(30) default 'D/10'"+" COMMENT '等待时间下一指令（单位：秒）'"
    )
    private Integer waitTimeNextCommand;
    @ManyToMany(mappedBy ="commands")
    private List<InstructionDefinition> instructionDefinitions;
    //自我关联——父子查询关系
    @ManyToOne
    @JoinColumn(name = "next_level_instruction")
    private Command command;

    public Command(String manufacturer,String name,String instructions ,String remarks, CheckingRules checkingRules, CommandTypeEnum commandType, String calculationFormula,String unit, Integer waitTimeNextCommand,Command command,Boolean isUse) {
        this.manufacturer = manufacturer;
        this.name = name;
        this.instructions = instructions;
        this.remarks = remarks;
        this.checkingRules = checkingRules;
        this.commandType = commandType;
        this.calculationFormula = calculationFormula;
        this.waitTimeNextCommand=waitTimeNextCommand;
        this.command=command;
        this.unit=unit;
        this.isUse=isUse;
    }

    public Command(Long id) {
        this.id = id;
    }

}
