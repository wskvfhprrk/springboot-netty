package com.hejz.dtu.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 传感器实体类
 * author: hejz
 * data: 2023-2-7
 */
@Getter
@Setter
@Entity(name = "equ_sensor")
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(appliesTo = "equ_sensor", comment = "传感器")
public class Sensor implements Serializable{

    @Id
    @SequenceGenerator(
            name = "equ_sensor_sequence",
            sequenceName = "sensor_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "equ_sensor_sequence"
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


    /**
     * 外键表——instruction_definition中的字段id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "max_instruction_definition_id")
    @JsonIgnoreProperties(value = {"sensors"})
    private InstructionDefinition maxInstructionDefinitionId;
    /**
     * 外键表——instruction_definition中的字段id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "min_instruction_definition_id")
        @JsonIgnoreProperties(value = {"sensors"})
    private InstructionDefinition minInstructionDefinitionId;

    /**
     * 外键表——tb_dtu_info中的字段id
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dtu_id")
    @JsonIgnoreProperties(value = {"sensors"})
    private DtuInfo dtuInfo;

    @OneToOne
    @JoinColumn(name = "command_id")
    private Command command;

    @Column(
            name = "sensor_sort",
            nullable = true,
            columnDefinition="varchar(10)"+" COMMENT '接收感应器数据排序'"
    )
    private Integer sensorSort;
    @Column(
            name = "unit",
            columnDefinition="varchar(15)"+" COMMENT '接收到数据的单位'"
    )
    private String unit;


    public Sensor(DtuInfo dtuInfo, String name, Integer max, Integer min, InstructionDefinition maxInstructionDefinitionId, InstructionDefinition minInstructionDefinitionId,Command command,Integer sensorSort,String unit) {
        this.name = name;
        this.max = max;
        this.min = min;
        this.maxInstructionDefinitionId = maxInstructionDefinitionId;
        this.minInstructionDefinitionId = minInstructionDefinitionId;
        this.dtuInfo = dtuInfo;
        this.command = command;
        this.sensorSort = sensorSort;
        this.unit = unit;
    }
}
