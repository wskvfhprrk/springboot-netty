package com.hejz.common;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 21:43
 * @Description: 常量类
 */
public class Constant {
    //数据检查规则redis缓存名称
    public static final String CHECKING_RULES_CACHE_KEY = "checkingRulesCacheKey";
    public static final String RELAY_CACHE_KEY = "relayCacheKey";
    public static final String SENSOR_CACHE_KEY = "sensorCacheKey";
    public static final String RELAY_DEFINITION_COMMAND_CACHE_KEY = "relayDefinitionCommandCacheKey";
    public static final String DTU_INFO_CACHE_KEY = "dtuInfoCacheKey";
    public static final String COMMAND_STATUS_CACHE_KEY = "commandStatusCacheKey";
    //IMEI长度
    public static final int IMEI_LENGTH = 15;
    //dut注册bytes长度
    public static final int DUT_REGISTERED_BYTES_LENGTH = 89;
    //DTU轮询返回长度
    public static final int DTU_POLLING_RETURN_LENGTH = 22;
    //继电器返回值长度
    public static final int RELAY_RETURN_VALUES_LENGTH = 23;
    //每组间隔时间（秒）
    public static final int INTERVAL_TIME = 50;
}
