package com.hejz.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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
            columnDefinition="int(15)"+" COMMENT '获取值参考最小值'"
    )
    private Integer min;
    /**
     * 超过最大值时的指令——控制器集的指领IDs，默认值为0不作处理，一般为双数字，第一个是继电器指令，第二个1代表是合，0代表是断开
     */
    @Column(
            name = "max_relay_definition_command_id",
            nullable = true,
            columnDefinition="bigint default 0"+" COMMENT '超过最大值时的指令'"
    )
    private Long maxRelayDefinitionCommandId = 0L;
    /**
     * 小于最小值时发出的指令——控制器集的指领IDs，默认值为0不作处理，一般为双数字，第一个是继电器指令，第二个1代表是合，0代表是断开
     */
    @Column(
            name = "min_relay_definition_command_id",
            nullable = true,
            columnDefinition = "bigint default 0" + " COMMENT '小于最小值时发出的指令'"
    )
    private Long minRelayDefinitionCommandId = 0L;

    @ManyToMany(mappedBy = "sensors")
    private List<InstructionDefinition> instructionDefinitions;

    public Sensor(Long dtuId, String name, Integer max, Integer min, Long maxRelayDefinitionCommandId, Long minRelayDefinitionCommandId) {
        this.dtuInfo = new DtuInfo(dtuId);
        this.name = name;
        this.max = max;
        this.min = min;
        this.maxRelayDefinitionCommandId = maxRelayDefinitionCommandId;

        this.minRelayDefinitionCommandId = minRelayDefinitionCommandId;
    }


}
