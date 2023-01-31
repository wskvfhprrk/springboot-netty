package com.hejz.dto;

import com.hejz.common.Page;
import lombok.Data;

import java.util.Date;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-31 09:00
 * @Description:
 */
@Data
public class SensorDataDbFindByPageDto extends Page {
    private Long id;
    private Date createDate;
    private Long dtuId;
    private String names;
    private String data;
    private String units;
}
