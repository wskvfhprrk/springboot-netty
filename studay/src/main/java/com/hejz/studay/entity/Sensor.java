package com.hejz.studay.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-03 07:26
 * @Description: 传感器类
 */
@Data
@Entity(name = "sensor")
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
    private String imei;
    /**
     * 感应器编号地址——发出接收时指令地址位（每个感应器都有一个地址位的）
     */
    private Integer adrss;
    /**
     * 名称
     */
    private String name;
    /**
     * 发送查询16进制指令字符串——含有crc16验证码
     */
    private String hex;
    /**
     * 为了校正上报数据与实际数据间的差距，使用到的计算公式——D是测得数据，如果实际数据是原来的小10倍加上5，公式为D/10+5,用测得的结果带入计算公式得到最后实际结果。
     */
    private String calculationFormula = "D/10";
    /**
     * 接收到数据的单位
     */
    private String unit;
    /**
     * 获取值参考最大值
     */
    private Integer max;
    /**
     * 获取值参考最小值
     */
    private Integer min;
    /**
     * 超过最大值时的指令——控制器集的指领IDs，默认值为0不作处理，一般为双数字，第一个是继电器指令，第二个1代表是合，0代表是断开
     */
    private String maxControIds = "0";
    /**
     * 小于最小值时发出的指令——控制器集的指领IDs，默认值为0不作处理，一般为双数字，第一个是继电器指令，第二个1代表是合，0代表是断开
     */
    private String minControIds = "0";


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAdrss() {
        return adrss;
    }

    public void setAdrss(int adrss) {
        this.adrss = adrss;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public String getCalculationFormula() {
        return calculationFormula;
    }

    public void setCalculationFormula(String calculationFormula) {
        this.calculationFormula = calculationFormula;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public String getMaxControIds() {
        return maxControIds;
    }

    public void setMaxControIds(String maxControIds) {
        this.maxControIds = maxControIds;
    }

    public String getMinControIds() {
        return minControIds;
    }

    public void setMinControIds(String minControIds) {
        this.minControIds = minControIds;
    }

    public void setAdrss(Integer adrss) {
        this.adrss = adrss;
    }

    public Sensor() {

    }

    public Sensor(Long id, String imei, int adrss, String name, String hex, String calculationFormula, String unit, Integer max, Integer min, String maxControIds, String minControIds) {
        this.id = id;
        this.imei = imei;
        this.adrss = adrss;
        this.name = name;
        this.hex = hex;
        this.calculationFormula = calculationFormula;
        this.unit = unit;
        this.max = max;
        this.min = min;
        this.maxControIds = maxControIds;
        this.minControIds = minControIds;
    }
}
