package com.hejz.enm;

/**
 *
 * @Description: 发送消息的动作 枚举
 */
public enum MsgActionEnum {
    BIND(1, "第一次(或重连)初始化连接"),
    SENSOR(2, "传感器消息"),
    RELAY(3, "继电器返回消息"),
    KEEP_ALIVE(4, "心跳消息");

    public final Integer type;
    public final String content;

    MsgActionEnum(Integer type, String content){
        this.type = type;
        this.content = content;
    }

    public Integer getType() {
        return type;
    }
}
