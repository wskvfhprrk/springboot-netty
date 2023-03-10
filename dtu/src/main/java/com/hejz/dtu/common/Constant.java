package com.hejz.dtu.common;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 21:43
 * @Description: 常量类
 */
public class Constant {
    //数据检查规则redis缓存名称
    public static final String SENSOR_CACHE_KEY = "sensorCacheKey";
    public static final String DTU_INFO_CACHE_KEY = "dtuInfoCacheKey";
    public static final String DTU_INFO_IMEI_CACHE_KEY = "dtuInfoImeiCacheKey";
    public static final String CACHE_INSTRUCTIONS_THAT_NEED_TO_CONTINUE_PROCESSING_CACHE_KEY = "cacheInstructionsThatNeedToContinueProcessingCacheKey";
    public static final String INSTRUCTION_DEFINITION_CACHE_KEY = "instructionDefinitionCacheKey";
    //最后时间——key为ctx.channel().id()
    public static final Map<String, LocalDateTime> END_TIME_MAP = new HashMap<>();
    //缓存每组dtu查询后返回的bytes值，够数量才解析，不够数量解析没有用——key为ctx.channel().id()
    public static final Map<String, List<byte[]>> SENSOR_DATA_BYTE_LIST_MAP = new HashMap<>();
    //记录三次临界值才执行——key为ctx.channel().id()+min或max
    public static final Map<String,List<Double>> THREE_RECORDS_MAP = new HashMap();
    //按通道存储每组上报间隔时间——在接到据后会自传感器数据后动修改与dtu设置的每组间隔时间一致，否则会是默认时间。
    public static final  Map<String,Integer> INTERVAL_TIME_MAP = new ConcurrentHashMap() ;
    public static final String GET_CHART_DATA_KEY = "GetChartDataKey";


    //channel绑定的dtuId的Key
    public static final String CHANNEl_KEY = "dtuId";
    //IMEI长度
    public static final int IMEI_LENGTH = 15;
    //dut注册bytes长度
    public static final int DUT_REGISTERED_BYTES_LENGTH = 89;
    //每组间隔默认时间（毫秒）
    public static final int INTERVAL_TIME = 30000;
    //netty客户端所有的连接绑定信息
    public static final Map<Long, Channel> USER_CHANNEL = new ConcurrentHashMap<>();
    //所有活动的客户端
    public static ChannelGroup CHANNELGROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    public static final int READ_IDEL_TIME_OUT = 180; // 读超时
    public static final int WRITE_IDEL_TIME_OUT = 60; // 写超时
    public static final int ALL_IDEL_TIME_OUT = 180; // 所有超时

}
