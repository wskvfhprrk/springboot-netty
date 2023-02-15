package com.hejz.dtu.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hejz.dtu.enm.InstructionTypeEnum;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * 继电器定义指令实体类
 * author: hejz
 * data: 2023-2-7
 */
@Getter
@Setter
@Entity(name = "equ_instruction_definition")
@org.hibernate.annotations.Table(appliesTo = "equ_instruction_definition", comment = "继电器定义指令")
@NoArgsConstructor
@AllArgsConstructor
public class InstructionDefinition implements Serializable{

    @Id
    @SequenceGenerator(
            name = "equ_instruction_definition_sequence",
            sequenceName = "equ_instruction_definition_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "equ_instruction_definition_sequence"
    )
    @Column(
            name = "id",
            nullable = false,
            columnDefinition="bigint"+" COMMENT '继电器定义指令ID'"
    )
    private Long id;

    @Column(
            name = "instruction_type",
            nullable = true,
            columnDefinition = "int(2)" + " COMMENT '指令类型'"
    )
    private InstructionTypeEnum instructionType;

    @Column(
            name = "name",
            nullable = false,
            columnDefinition="varchar(255)"+" COMMENT '名称'"
    )
    private String name;

    @Column(
            name = "remarks",
            nullable = true,
            columnDefinition = "varchar(255)" + " COMMENT '备注'"
    )
    private String remarks;

    /**
     * 外键表——tb_dtu_info中的字段id
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dtu_id")
    @JsonIgnoreProperties(value = {"instructionDefinitions"})
    private DtuInfo dtuInfo;

    @JsonIgnoreProperties(value = {"instructionDefinitions"})
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "mid_instruction_definition_command",
            inverseJoinColumns = @JoinColumn(name = "command_id", referencedColumnName = "id"),
            joinColumns = @JoinColumn(name = "instruction_definition_id", referencedColumnName = "id"))
    private Set<Command> commands;

    public InstructionDefinition(DtuInfo dtuInfo, String name, String remarks, InstructionTypeEnum instructionType, Set<Command> commands) {
        this.instructionType = instructionType;
        this.name = name;
        this.remarks = remarks;
        this.dtuInfo = dtuInfo;
        this.commands = commands;
    }

    /**
     * 发送指令时间，不需要向数据库中写入
     */
    @Transient
    private LocalDateTime sendCommandTime;

}
