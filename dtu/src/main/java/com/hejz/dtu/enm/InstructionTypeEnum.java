package com.hejz.dtu.enm;

/**
 * 指令类型——要求排序奇偶是相反指令
 */
public enum InstructionTypeEnum {
    //打开通风
    OPEN_VENTILATION,
    //关闭通风
    CLOSE_VENTILATION,
    //打开浇水
    TURN_ON_WATERING,
    //关闭浇水
    TURN_OFF_WATERING,
    //打开光照
    TURN_ON_LIGHTING,
    //关闭光照
    TURN_OFF_LIGHTING,
    //打开其它
    OPEN_OTHER,
    //关闭其它
    CLOSE_OTHERS,
    //重置指令
    SENSOR_COMMAND;
}
