package com.hejz.nettyserver;

import com.hejz.common.Constant;
import com.hejz.entity.*;
import com.hejz.service.*;
import com.hejz.utils.HexConvert;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.script.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-14 08:45
 * @Description: 处理感应器返回值处理
 */
@Component
@Slf4j
public class ProcessSensorReturnValue {
    @Autowired
    private SensorService sensorService;
    @Autowired
    private DtuInfoService dtuInfoService;
    @Autowired
    SensorDataDbService sensorDataDbService;
    @Autowired
    RelayDefinitionCommandService relayDefinitionCommandService;
    @Autowired
    CheckingRulesService checkingRulesService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    DtuRegister dtuRegister;
    @Autowired
    private ProcessRelayCommands processRelayCommands;

    /**
     * 收集感应器数据
     *
     * @param ctx   通道上下文
     * @param bytes 收到byte[]信息
     */
    public void start(ChannelHandlerContext ctx, byte[] bytes) {
        DtuInfo dtuInfo;
        AttributeKey<Long> key = AttributeKey.valueOf(Constant.CHANNEl_KEY);
        Long dtuId = ctx.channel().attr(key).get();
        if (dtuId != null) {
            dtuInfo = dtuInfoService.findById(dtuId);
        } else {
            dtuInfo = NettyServiceCommon.calculationDtuInfo(bytes);
            dtuRegister.register(ctx, dtuInfo);
        }
        //同步每次轮询间隔时间
        //把间隔时间设置为每个所在dtu间隔发送时间
        Constant.INTERVAL_TIME_MAP.put(ctx.channel().id().toString(), dtuInfo.getIntervalTime());
        int sensorsLength = sensorService.findAllByDtuId(dtuInfo.getId()).size();
        /**
         * 每组数据以上报时间差值最大的为一组的第一个数据为标准——发送数据时间间隔大于等于周周期时间的视为第一个数据
         */
        LocalDateTime dateIntervalTime = LocalDateTime.now().minusSeconds(dtuInfo.getIntervalTime() / 1000 + 30);
        //首次end时间为空值
        LocalDateTime end = Constant.END_TIME_MAP.get(ctx.channel().id().toString()) == null ? dateIntervalTime : Constant.END_TIME_MAP.get(ctx.channel().id().toString());
        Duration duration = Duration.between(end, LocalDateTime.now());
        //两个时间，多加一秒时间，防止出错
        long millis = duration.toMillis() + 1000;
        List<byte[]> sensorDataByteList;
        //必须检测是有用的数据才可以，如果不能够使用才不可以
        if (!NettyServiceCommon.testingData(bytes)) return;
//        log.info("millis=={},dtuInfo.getIntervalTime()=={}",millis,dtuInfo.getIntervalTime());
        if (millis >= dtuInfo.getIntervalTime()) {
            log.info("======={}=>{}==>查询一组出数据===========", ctx.channel().id().toString(), dtuInfo.getId());
            sensorDataByteList = new ArrayList<>(sensorsLength);
            sensorDataByteList.add(bytes);
            Constant.SENSOR_DATA_BYTE_LIST_MAP.put(ctx.channel().id().toString(), sensorDataByteList);
            Constant.END_TIME_MAP.put(ctx.channel().id().toString(), LocalDateTime.now());
        } else {
            sensorDataByteList = Constant.SENSOR_DATA_BYTE_LIST_MAP.get(ctx.channel().id().toString());
            sensorDataByteList.add(bytes);
            Constant.SENSOR_DATA_BYTE_LIST_MAP.put(ctx.channel().id().toString(), sensorDataByteList);
            Constant.END_TIME_MAP.put(ctx.channel().id().toString(), LocalDateTime.now());
        }
        if (Constant.SENSOR_DATA_BYTE_LIST_MAP.get(ctx.channel().id().toString()).size() == sensorsLength) {
            log.info("======{}=>{}=>解析一组出数据=========", ctx.channel().id().toString(), dtuInfo.getId());
            try {
                List<SensorData> sensorDataList = parseSensorListData(Constant.SENSOR_DATA_BYTE_LIST_MAP.get(ctx.channel().id().toString()), ctx);
                //插入数据库
                insertDatabase(dtuInfo.getId(), sensorDataList);
            } catch (Exception e) {
                log.info(e.toString());
            }
            //需要重置数据
            Constant.SENSOR_DATA_BYTE_LIST_MAP.remove(ctx.channel().id().toString());
        }
    }

    /**
     * 批量解析收到的数据
     *
     * @param list
     * @param ctx  通道上下文
     * @return
     */
    private List<SensorData> parseSensorListData(List<byte[]> list, ChannelHandlerContext ctx) throws Exception {
        List<SensorData> doubleList = new ArrayList<>();
        //按顺序解析，根据sensor顺序解析找对应关系
        for (int i = 0; i < list.size(); i++) {
            Double aDouble = parseSensorOneData(list.get(i), i, ctx);
            DtuInfo dtuInfo = NettyServiceCommon.calculationDtuInfo(list.get(0));
            List<Sensor> sensors = sensorService.findAllByDtuId(dtuInfo.getId());
            Sensor sensor = sensors.get(i);
            SensorData sensorData = new SensorData(i, sensor.getName(), aDouble, sensor.getUnit());
            doubleList.add(sensorData);
        }
        return doubleList;
    }

    /**
     * 添加到数据库中
     *
     * @param dtuId
     * @param sensorDataList
     */
    private void insertDatabase(Long dtuId, List<SensorData> sensorDataList) throws Exception {
        List<String> nameList = sensorDataList.stream().map(sensorData -> sensorData.getName().trim()).collect(Collectors.toList());
        String names = StringUtils.join(nameList, ",");
        List<String> dataList = sensorDataList.stream().map(sensorData -> sensorData.getData().toString()).collect(Collectors.toList());
        String data = StringUtils.join(dataList, ",");
        List<String> unitList = sensorDataList.stream().map(sensorData -> sensorData.getUnit()).collect(Collectors.toList());
        String units = StringUtils.join(unitList, ",");
        //存进数据
        SensorDataDb sensorDataDb = new SensorDataDb(new Date(), dtuId, names, data, units);
        sensorDataDbService.save(sensorDataDb);
    }

    /**
     * 传感器接收数据解析——温湿度和土壤一样规则
     *
     * @param bytes       收到byte[]信息——22长度
     * @param arrayNumber 数组编号
     * @param ctx         通道上下文
     */
    private Double parseSensorOneData(byte[] bytes, int arrayNumber, ChannelHandlerContext ctx) throws Exception {
        ////有用的bytes[]的值
        int useLength = bytes.length - Constant.IMEI_LENGTH;
        byte[] useBytes = NettyServiceCommon.getUseBytes(bytes, useLength);
        // TODO: 2023/1/13 计算返回值
        Integer x = calculateReturnValue(useBytes);
        //获取数据值
        double d = Double.parseDouble(String.valueOf(x));
        DtuInfo dtuInfo = NettyServiceCommon.calculationDtuInfo(bytes);
        Sensor sensor = sensorService.findAllByDtuId(dtuInfo.getId()).get(arrayNumber);
        //经过公式计算得到实际结果
        Double actualResults = calculateActualData(sensor.getCalculationFormula(), d);
        log.info(" 通道：{} dtuId==>{},{} =====> {}  ====> {}  ====> {}", ctx.channel().id().toString(), dtuInfo.getId(), arrayNumber, sensor.getAdrss(), sensor.getName(), actualResults + sensor.getUnit());
        //开新建程——异步处理根据解析到数据大小判断是否产生事件
        if (dtuInfo.getAutomaticAdjustment()) {
            new Thread(() -> {
                processRelayCommands.handleAccordingToRelayCommand(sensor, actualResults, ctx);
            }).start();
        }
        return actualResults;
    }

    private Integer calculateReturnValue(byte[] bytes) throws Exception {
        List<CheckingRules> checkingRules = checkingRulesService.getByCommonLength(bytes.length);
        List<Integer> list = checkingRules.stream().map(checkingRule -> {
            int dataBegin = checkingRule.getAddressBitLength() + checkingRule.getFunctionCodeLength() + checkingRule.getDataBitsLength();
            byte[] dataLength = new byte[checkingRule.getDataValueLength()];
            System.arraycopy(bytes, dataBegin, dataLength, 0, checkingRule.getDataValueLength());
            int i = 0;
            try {
                i = HexToInt(dataLength);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            return i;
        }).collect(Collectors.toList());
        //todo 此处有多个相同值时会问题
        if (list.size() > 1) {
            log.error("有多个相同的规则——总长度为：{}，此处还要开发！", checkingRules.get(0).getCommonLength());
        }
        return list.get(0);
    }

    /**
     * 计算实际数据
     *
     * @param formula     公式
     * @param measureData 测量数据
     * @return
     */
    private Double calculateActualData(String formula, double measureData) throws Exception {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
        Compilable compilable = (Compilable) engine;
        Bindings bindings = engine.createBindings(); //Local级别的Binding
        CompiledScript JSFunction; //解析编译脚本函数
        try {
            JSFunction = compilable.compile(formula);
            bindings.put("D", measureData);
            Object result = JSFunction.eval(bindings);
//            log.info(result); //调用缓存着的脚本函数对象，Bindings作为参数容器传入
            return Double.valueOf(result.toString());
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Integer HexToInt(byte[] bytes) throws Exception {
        String dataHex = HexConvert.BinaryToHexString(bytes).replaceAll(" ", "");
        String hex = "0x" + dataHex;
        Integer x = Integer.parseInt(hex.substring(2), 16);//从第2个字符开始截取
        return x;
    }


}
