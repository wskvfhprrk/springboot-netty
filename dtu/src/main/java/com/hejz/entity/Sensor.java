package com.hejz.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-03 07:26
 * @Description: 传感器类
 */
@Data
@Entity(name = "sensor")
@org.hibernate.annotations.Table(appliesTo = "sensor", comment = "传感器")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class Sensor implements Serializable {

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
            columnDefinition = "bigint" + " COMMENT 'ID'"
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dtu_id", nullable = false)
    private DtuInfo dtuInfo;

    /**
     * 名称
     */
    @Column(
            name = "name",
            nullable = true,
            columnDefinition = "varchar(15)" + " COMMENT '名称'"
    )
    private String name;

    /**
     * 获取值参考最大值
     */
    @Column(
            name = "max",
            nullable = true,
            columnDefinition="int(15)"+" COMMENT '获取值参考最大值'"
    )
    private Integer max;
    /**
     * 获取值参考最小值
     */
    @Column(
            name = "min",
            nullable = true,
            columnDefinition = "int(15)" + " COMMENT '获取值参考最小值'"
    )
    private Integer min;
    @ManyToOne
    @JoinColumn(name = "max_instruction_definition_id", updatable = false)
    private InstructionDefinition maxInstructionDefinitionId;
    /**
     * 小于最小值时发出的指令
     */

    @ManyToOne
    @JoinColumn(name = "min_instruction_definition_id", updatable = false)
    private InstructionDefinition minInstructionDefinitionId;

    public Sensor(DtuInfo dtuInfo, String name, Integer max, Integer min, InstructionDefinition maxInstructionDefinitionId, InstructionDefinition minInstructionDefinitionId) {
        this.dtuInfo = dtuInfo;
        this.name = name;
        this.max = max;
        this.min = min;
        this.maxInstructionDefinitionId = maxInstructionDefinitionId;
        this.minInstructionDefinitionId = minInstructionDefinitionId;
    }


}
