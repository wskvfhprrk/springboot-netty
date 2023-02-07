package com.hejz.dtu.entity;

import lombok.Data;
import java.io.Serializable;
import javax.persistence.*;

/**
 * 指令实体类
 * author: hejz
 * data: 2023-2-7
 */
@Data
@Entity(name = "tb_command")
@org.hibernate.annotations.Table(appliesTo = "tb_command", comment = "指令")
public class Command implements Serializable{

    @Id
    @SequenceGenerator(
            name = "tb_command_sequence",
            sequenceName = "tb_command_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tb_command_sequence"
    )
    @Column(
            name = "id",
            nullable = false,
            columnDefinition="bigint"+" COMMENT 'ID'"
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
            columnDefinition="int"+" COMMENT '指令类型'"
    )
    private Integer commandType;

    @Column(
            name = "instructions",
            nullable = false,
            columnDefinition="varchar(100)"+" COMMENT '指令'"
    )
    private String instructions;

    @Column(
            name = "is_use",
            nullable = false,
            columnDefinition="date"+" COMMENT '是否正在使用——true正在使用，flase没有使用'"
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
            nullable = true,
            columnDefinition="varchar(255)"+" COMMENT '备注'"
    )
    private String remarks;

    @Column(
            name = "unit",
            nullable = true,
            columnDefinition="varchar(15)"+" COMMENT '接收到数据的单位'"
    )
    private String unit;

    @Column(
            name = "wait_time_next_command",
            nullable = true,
            columnDefinition="varchar(30)"+" COMMENT '等待时间下一指令（单位：秒）'"
    )
    private String waitTimeNextCommand;

    @Column(
            name = "checking_rules_id",
            nullable = true,
            columnDefinition="int"+" COMMENT 'ID'"
    )
    private Integer checkingRulesId;

    @Column(
            name = "next_level_instruction",
            nullable = true,
            columnDefinition="bigint"+" COMMENT 'ID'"
    )
    private Long nextLevelInstruction;
    /**
     * 外键表——checking_rules中的字段id
     */
    @ManyToOne
    @JoinColumn(name = "checking_rules_id",insertable = false,updatable = false)
    private CheckingRules CheckingRules;
    /**
     * 外键表——tb_command中的字段id
     */
    @ManyToOne
    @JoinColumn(name = "next_level_instruction",insertable = false,updatable = false)
    private Command Command;
}
