package com.hejz.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hejz.enm.InstructionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-11 08:04
 * @Description: 指令定义
 */
@Data
@Entity(name = "instruction_definition")
@org.hibernate.annotations.Table(appliesTo = "instruction_definition", comment = "继电器定义指令")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class InstructionDefinition implements Serializable {
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
    //ID
    @Column(
            name = "id",
            nullable = false,
            columnDefinition = "bigint" + " COMMENT 'ID'"
    )
    private Long id;

    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "varchar(255)" + " COMMENT '名称'"
    )
    private String name;
    //备注
    @Column(
            name = "remarks",
            nullable = true,
            columnDefinition="varchar(255)"+" COMMENT '备注'"
    )
    private String remarks;
    @Column(
            name = "instruction_type",
            columnDefinition = "int(2)" + " COMMENT '指令类型'"
    )
    private InstructionTypeEnum instructionType;
    @ManyToOne
    @JoinColumn(name = "dtu_id", nullable = false)
    private DtuInfo dtuInfo;
//    @OneToOne(optional = false,mappedBy = "instructionDefinition")
//    private Sensor sensor;
    @ManyToMany
    @JoinTable(name = "instruction_definition_command",joinColumns=
            @JoinColumn(name="command_id", referencedColumnName="id"),
            inverseJoinColumns=
            @JoinColumn(name="instruction_definition_id", referencedColumnName="id"))
    private Set<Command> commands;
    //发送指令时间，超过1小时不再发送指令了
    @Transient
    private LocalDateTime sendCommandTime;


    public InstructionDefinition(DtuInfo dtuInfo, String name, String remarks, InstructionTypeEnum instructionType,Set<Command> commands) {
        this.dtuInfo = dtuInfo;
        this.name = name;
        this.remarks = remarks;
        this.instructionType = instructionType;
        this.commands = commands;
    }
}
