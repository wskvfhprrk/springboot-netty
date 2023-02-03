package com.hejz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-02-03 10:47
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtuInfoDto {
    private String imei;
    private String sensorAddressOrder;
    private Integer registrationLength = 89;
    private Integer imeiLength = 15;
    private String relayCheckingRulesIds;
    private String sensorCheckingRulesIds;
    private Integer heartbeatLength=2;
    private Integer intervalTime;
    private Boolean automaticAdjustment = true;
    private Boolean noImei = true;
}
