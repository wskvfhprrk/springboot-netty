package com.hejz.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-03 07:26
 * @Description: 传感器类
 */
@Data
@Entity(name = "sensor")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class Sensor {

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
            columnDefinition="bigint"+" COMMENT 'ID'"
    )
    private Long id;
    /**
     * 每个控制器都有一个唯一的imei值对应——dur信息中的imei值
     */
    @Column(
            name = "imei",
            nullable = true,
            columnDefinition="varchar(15)"+" COMMENT '每个控制器都有一个唯一的imei值对应——dur信息中的imei值'"
    )
    private String imei;
    /**
     * 感应器编号地址——发出接收时指令地址位（每个感应器都有一个地址位的）
     */
    @Column(
            name = "adrss",
            nullable = true,
            columnDefinition="int(2)"+" COMMENT '感应器编号地址——发出接收时指令地址位（每个感应器都有一个地址位的）'"
    )
    private Integer adrss;
    /**
     * 名称
     */
    @Column(
            name = "name",
            nullable = true,
            columnDefinition="varchar(15)"+" COMMENT '名称'"
    )
    private String name;
    /**
     * 发送查询16进制指令字符串——含有crc16验证码
     */
    @Column(
            name = "hex",
            nullable = true,
            columnDefinition="varchar(255)"+" COMMENT '发送查询16进制指令字符串——含有crc16验证码'"
    )
    private String hex;
    /**
     * 为了校正上报数据与实际数据间的差距，使用到的计算公式——D是测得数据，如果实际数据是原来的小10倍加上5，公式为D/10+5,用测得的结果带入计算公式得到最后实际结果。
     */
    @Column(
            name = "calculation_formula",
            nullable = true,
            columnDefinition="varchar(30) default 'D/10'"+" COMMENT '为了校正上报数据与实际数据间的差距，使用到的计算公式——D是测得数据，如果实际数据是原来的小10倍加上5，公式为D/10+5,用测得的结果带入计算公式得到最后实际结果。'"
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
            columnDefinition="bigint default 0"+" COMMENT '小于最小值时发出的指令'"
    )
    private Long minRelayDefinitionCommandId = 0L;

    public Sensor( String imei, Integer adrss, String name, String hex, String calculationFormula, String unit, Integer max, Integer min, Long maxRelayDefinitionCommandId, Long minRelayDefinitionCommandId) {
        this.imei = imei;
        this.adrss = adrss;
        this.name = name;
        this.hex = hex;
        this.calculationFormula = calculationFormula;
        this.unit = unit;
        this.max = max;
        this.min = min;
        this.maxRelayDefinitionCommandId = maxRelayDefinitionCommandId;
        this.minRelayDefinitionCommandId = minRelayDefinitionCommandId;
    }

}
