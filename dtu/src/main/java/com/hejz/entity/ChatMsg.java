package com.hejz.entity;

import lombok.Data;


/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-02-01 06:36
 * @Description: http请求消息转给tcp
 */
@Data
public class ChatMsg {
    private Long dtuId;
    private String msg;
}
