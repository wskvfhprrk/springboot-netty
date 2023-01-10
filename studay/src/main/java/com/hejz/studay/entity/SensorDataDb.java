package com.hejz.studay.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-09 10:00
 * @Description: 传感器数据存放进数据库
 */
@Data
@Entity(name = "sensor_data_db")
@NoArgsConstructor
@AllArgsConstructor
public class SensorDataDb {
    @Id
    private Date date;
    private String imei;
    private String names;
    private String datas;
    private String units;

}
