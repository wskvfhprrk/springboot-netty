package com.hejz.studay.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-03 12:39
 * @Description: dtu信息
 */
@Data
@Entity(name = "dtu_info")
public class DtuInfo {
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
    private String imei;

    /**
     * dtu注册信息的长度
     */
    private Integer registrationLength = 89;
    /**
     * imei长度固定值
     */
    private Integer imeiLength = 15;
    /**
     * 继电器返回值长度
     */
    private Integer relayLength=8;
    /**
     * 感应器返回值长度
     */
    private Integer sensorLength=7;
    /**
     * 心跳bates长度
     */
    private Integer heartbeatLength;
    /**
     * 每组发送接收间隔时间(毫秒)——略小于dtu每组间隔时间，大于每组中每个发送间隔时间
     */
    private Integer groupIntervalTime;
    /**
     * 传感器指中指令参数
     */
    @Transient
    private List<Sensor> sensorList;
    /**
     * 控制器指令集
     */
    @Transient
    private List<Relay> relayList;
    /**
     * 是否自动调整
     */
    private Boolean automaticAdjustment = true;

}
