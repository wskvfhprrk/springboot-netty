package com.hejz.dtu.vo;

import com.hejz.dtu.entity.User;
import lombok.Data;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-02-18 17:09
 * @Description:
 */
@Data
public class DtuInfoFindAllVo {
    private Long id;
    private Boolean automaticAdjustment;
    private String imei;
    private Integer intervalTime;
    private Integer registrationLength;
    private String sensorAddressOrder;
    private User user;
}
