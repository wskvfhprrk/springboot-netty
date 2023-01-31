package com.hejz.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-31 08:58
 * @Description:
 */
@Data
public class SensorDataDbFindByPageVo {
    private Long id;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    private String names;
    private String data;
    private String units;
}
