package com.hejz.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-03 12:39
 * @Description: dtu信息
 */
@Data
@Entity(name = "dtu_info")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class DtuInfo implements Serializable {
    @Id
    @SequenceGenerator(
            name = "dtu_info_sequence",
            sequenceName = "dtu_info_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "dtu_info_sequence"
    )
    @Column(
            name = "id",
            nullable = false,
            columnDefinition="bigint"+" COMMENT 'ID'"
    )
    private Long id;
    @Column(
            name = "imei",
            nullable = false,
            columnDefinition="varchar(15)"+" COMMENT 'imei'"
    )
    private String imei;

    /**
     * dtu注册信息的长度
     */
    @Column(
            name = "registration_length",
            columnDefinition="int(2) default 89"+" COMMENT 'dtu注册信息的长度'"
    )
    private Integer registrationLength = 89;
    /**
     * imei长度固定值
     */
    @Column(
            name = "imei_length",
            columnDefinition="int(2) default 15"+" COMMENT 'imei长度固定值'"
    )
    private Integer imeiLength = 15;
    /**
     * 继电器返回值检测规则id
     */
    @Column(
            name = "relay_checking_rules_id",
            columnDefinition="int(2) default 8"+" COMMENT '继电器返回值检测规则id'"
    )
    private Integer relayCheckingRulesId=2;
    /**
     * 感应器返回值检测规则id
     */
    @Column(
            name = "sensor_checking_rules_id",
            columnDefinition="int(2) default 7"+" COMMENT '感应器返回值检测规则id'"
    )
    private Integer sensorCheckingRulesId=1;
    /**
     * 心跳bates长度
     */
    @Column(
            name = "heartbeat_length",
            columnDefinition="int(2) default 2"+" COMMENT '心跳bates长度'"
    )
    private Integer heartbeatLength=2;
    /**
     * 每组发送接收间隔时间(毫秒)和心跳时间——略小于dtu每组间隔时间，大于每组中每个发送间隔时间
     */
    @Column(
            name = "interval_time",
            nullable = true,
            columnDefinition="int(10)"+" COMMENT '每组发送接收间隔时间(毫秒)——与dtu每组间隔时间要一致'"
    )
    private Integer intervalTime;
    /**
     * 是否自动控制——1是自动0是手动控制
     */
    @Column(
            name = "automatic_adjustment",
            nullable = true,
            columnDefinition="bit(1)"+" COMMENT '是否自动控制——1是自动0是手动控制'"
    )
    private Boolean automaticAdjustment = true;
    @Column(
            name = "no_imei",
            nullable = true,
            columnDefinition="bit(1)"+" COMMENT '返回值是否带imei'"
    )
    private Boolean noImei = true;

    public DtuInfo() {
    }

    public DtuInfo(String imei, Integer registrationLength, Integer imeiLength, Integer relayCheckingRulesId, Integer sensorCheckingRulesId, Integer heartbeatLength, Integer intervalTime, Boolean automaticAdjustment,Boolean noImei) {
        this.imei = imei;
        this.registrationLength = registrationLength;
        this.imeiLength = imeiLength;
        this.relayCheckingRulesId = relayCheckingRulesId;
        this.sensorCheckingRulesId = sensorCheckingRulesId;
        this.heartbeatLength = heartbeatLength;
        this.intervalTime = intervalTime;
        this.automaticAdjustment = automaticAdjustment;
        this.noImei = noImei;
    }
}
