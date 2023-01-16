package com.hejz.common;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public static final String CACHE_INSTRUCTIONS_THAT_NEED_TO_CONTINUE_PROCESSING_CACHE_KEY = "cacheInstructionsThatNeedToContinueProcessingCacheKey";
    public static final String COMMAND_STATUS_CACHE_KEY = "commandStatusCacheKey";
    //最后时间——key为ctx.channel().id()
    public static final Map<String, LocalDateTime> END_TIME_MAP = new HashMap<>();
    //缓存每组dtu查询后返回的bytes值，够数量才解析，不够数量解析没有用——key为ctx.channel().id()
    public static final Map<String, List<byte[]>> SENSOR_DATA_BYTE_LIST_MAP = new HashMap<>();
    //记录三次临界值才执行——key为ctx.channel().id()+min或max
    public static final Map<String,List<Double>> THREE_RECORDS_MAP = new HashMap();
    //继电器状态值记录——key为ctx.channel().id()
    public static final Map<Long, Integer> COMMAND_STATUS_MAP = new HashMap<>();
    //IMEI长度
    public static final int IMEI_LENGTH = 15;
    //dut注册bytes长度
    public static final int DUT_REGISTERED_BYTES_LENGTH = 89;
    //DTU轮询返回长度
    public static final int DTU_POLLING_RETURN_LENGTH = 22;
    //继电器返回值长度
    public static final int RELAY_RETURN_VALUES_LENGTH = 23;
    //每组间隔默认时间（毫秒）
    public static final int INTERVAL_TIME = 30000;
    //按通道存储每组上报间隔时间——在接到据后会自传感器数据后动修改与dtu设置的每组间隔时间一致，否则会是默认时间。
    public static final  Map<String,Integer> INTERVAL_TIME_MAP = new HashMap() ;
    //需要继续处理缓存键的缓存指令失效时间（毫秒）
    public static final long EXPIRATION_TIME_OF_CACHE_INSTRUCTIONS_THAT_NEED_TO_CONTINUE_PROCESSING_CACHE_KEYS = 600000;
    //netty客户端所有的连接
    public static ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
}
