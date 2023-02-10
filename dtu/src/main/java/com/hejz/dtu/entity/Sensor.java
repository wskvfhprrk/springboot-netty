package com.hejz.dtu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 传感器实体类
 * author: hejz
 * data: 2023-2-7
 */
@Data
@Entity(name = "sensor")
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(appliesTo = "sensor", comment = "传感器")
public class Sensor implements Serializable{

    @Id
    @SequenceGenerator(
            name = "sensor_sequence",
            sequenceName = "sensor_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sensor_sequence"
    )
    @Column(
            name = "id",
            nullable = false,
            columnDefinition = "bigint" + " COMMENT '传感器ID'"
    )
    private Long id;

    @Column(
            name = "name",
            nullable = true,
            columnDefinition = "varchar(15)" + " COMMENT '名称'"
    )
    private String name;
    @Column(
            name = "max",
            nullable = true,
            columnDefinition = "int" + " COMMENT '获取值参考最大值'"
    )
    private Integer max;

    @Column(
            name = "min",
            nullable = true,
            columnDefinition="int"+" COMMENT '获取值参考最小值'"
    )
    private Integer min;

    @Column(
            name = "unit",
            nullable = true,
            columnDefinition="varchar(10)"+" COMMENT '接收传感器数据单位'"
    )
    private String unit;
    @Column(
            name = "command_sort",
            nullable = true,
            columnDefinition="varchar(10)"+" COMMENT '接收命令排序'"
    )
    private String commandSort;

    /**
     * 外键表——instruction_definition中的字段id
     */
    @ManyToOne
    @JoinColumn(name = "max_instruction_definition_id",insertable = false,updatable = false)
    private InstructionDefinition maxInstructionDefinitionId;
    /**
     * 外键表——instruction_definition中的字段id
     */
    @ManyToOne
    @JoinColumn(name = "min_instruction_definition_id", insertable = false, updatable = false)
    private InstructionDefinition minInstructionDefinitionId;

    @ManyToOne
    @JoinColumn(name = "command_id")
    private Command command;

    /**
     * 外键表——tb_dtu_info中的字段id
     */
    @ManyToOne
    @JoinColumn(name = "dtu_id", insertable = false, updatable = false)
    private DtuInfo dtuInfo;

    public Sensor(DtuInfo dtuInfo, String name, Integer max, Integer min, InstructionDefinition maxInstructionDefinitionId, InstructionDefinition minInstructionDefinitionId) {
        this.name = name;
        this.max = max;
        this.min = min;
        this.maxInstructionDefinitionId = maxInstructionDefinitionId;
        this.minInstructionDefinitionId = minInstructionDefinitionId;
        this.dtuInfo = dtuInfo;
    }
}
