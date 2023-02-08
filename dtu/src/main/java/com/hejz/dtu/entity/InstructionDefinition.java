package com.hejz.dtu.entity;

import com.hejz.dtu.enm.InstructionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * 继电器定义指令实体类
 * author: hejz
 * data: 2023-2-7
 */
@Data
@Entity(name = "instruction_definition")
@org.hibernate.annotations.Table(appliesTo = "instruction_definition", comment = "继电器定义指令")
@NoArgsConstructor
@AllArgsConstructor
public class InstructionDefinition implements Serializable{

    @Id
    @SequenceGenerator(
            name = "instruction_definition_sequence",
            sequenceName = "instruction_definition_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "instruction_definition_sequence"
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
    @ManyToOne
    @JoinColumn(name = "dtu_id", insertable = false, updatable = false)
    private DtuInfo dtuInfo;

    @ManyToMany(mappedBy = "instructionDefinitions")
    private Set<Command> commands;

    public InstructionDefinition(DtuInfo dtuInfo, String name, String remarks, InstructionTypeEnum instructionType,Set<Command> commands) {
        this.instructionType = instructionType;
        this.name = name;
        this.remarks = remarks;
        this.dtuInfo = dtuInfo;
        this.commands = commands;
    }
}
