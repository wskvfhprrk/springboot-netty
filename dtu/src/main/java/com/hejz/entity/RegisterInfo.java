package com.hejz.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-03 07:32
 * @Description: dtu注册时格式解析——{"fver":"YED_DTU_1.2.3","iccid":"89861122222045681451","imei":"865328063321359","csq":21}
 */
@Data
public class RegisterInfo implements Serializable {
    /**
     * dtu型号版本
     */
    private String fver;
    /**
     * dtu的iccid
     */
    private String iccid;
    /**
     * imei值
     */
    private String imei;
    /**
     * 信号强度
     */
    private String csq;

}
