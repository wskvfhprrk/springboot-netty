package com.hejz.dtu.enm;
/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-02-05 13:39
 * @Description: 指令——感应器和继电器
 */
public enum CommandTypeEnum {
    //感应器发送指令
    SENSOR_SENDS_COMMAND,
    //继电器闭合电路
    RELAY_CLOSE_CIRCUIT,
    //继电器断开电路
    RELAY_OPEN_CIRCUIT
    ;
}
