package com.hejz.dtu.entity;

import com.sun.xml.bind.WhiteSpaceProcessor;
import lombok.Data;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-02-10 00:13
 * @Description:
 */
@Data
public class RegisterInfo {
    private String imei;
    private String fver;
    private String iccid;
    private String csq;
}
