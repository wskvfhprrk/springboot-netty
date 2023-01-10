package com.hejz.studay.nettyserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hejz.studay.entity.*;
import com.hejz.studay.utils.CRC16;
import com.hejz.studay.utils.HexConvert;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.script.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
@ChannelHandler.Sharable
public class NettyServerHandler extends SimpleChannelInboundHandler {
    private Logger log = LogManager.getLogger(NettyServerHandler.class);
    @Autowired
    private ObjectMapper objectMapper;
    //所有的连接
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    //所有查询到的所有dtu的信息
    private static final List<DtuInfo> dtuInfos = new ArrayList<>();
    //记录imei中传感器值——缓存
    private static final Map<String, List<Sensor>> sensorDataArrMap = new HashMap<>();
    //记录imei中继电器值——缓存
    private static final Map<String, List<Relay>> relayDataArrMap = new HashMap<>();
    //dtu注册信息的长度
    private static final int registrationLength = 89;
    //imei长度固定值
    private static final int imeiNum = 15;
    //最后一条记录时间——用于计算最后一次返回dtu轮询值时间间隔时间确定每组数据定位
    private static final Map<String, LocalDateTime> endTimeMap = new HashMap<>();
    //缓存每组dtu查询后返回的bytes值，够数量才解析，不够数量解析没有用
    private static final Map<String, List<byte[]>> sensorDataByteListMap = new HashMap<>();
    //记录当前iemi中的dtuInfo信息
    private static final Map<String, DtuInfo> dtuInfoMap = new HashMap<>();
    //继电器状态值记录
    private static final Map<Long, Integer> relayStatusMap = new HashMap<>();
    //发出有效指令——需要发送反指令的
    private static final Map<String, List<String>> validInstructionsMap = new HashMap<>();

    /**
     * 设置设备信息——要使用数据固化和缓存
     *
     * @param imei 串号
     */
    private List<Sensor> setDeviceInformation(String imei) {
        log.info("初始化缓存数据…………");
        // TODO: 2023/1/5 改为从缓存和数据库中获取
        if (dtuInfos.isEmpty()) {
            DtuInfo dtuInfo = new DtuInfo();
            dtuInfo.setId(1L);
            dtuInfo.setImei(imei);
            dtuInfo.setImeiLength(15);
            dtuInfo.setRelayLength(8);
            dtuInfo.setSensorLength(7);
            dtuInfo.setGroupIntervalTime(2000);
            dtuInfo.setHeartbeatLength(2);
            dtuInfo.setRegistrationLength(89);
            dtuInfo.setAutomaticAdjustment(true);
            //感应器指令集
            List<Sensor> sensors = sensorDataArrMap.get(imei) == null ? new ArrayList<>() : sensorDataArrMap.get(imei);
            if (sensors.isEmpty()) {
                sensors.add(new Sensor(1L, "865328063321359", 1, "空气温度 ", "01 03 03 00 00 01 84 4E", "D/10", "ºC", 25, 20, "2-1,1-1", "2-1,1-0"));
                sensors.add(new Sensor(2L, "865328063321359", 1, "空气湿度 ", "01 03 03 01 00 01 D5 8E", "D/10", "%", 90, 70, "0", "0"));
                sensors.add(new Sensor(3L, "865328063321359", 2, "土壤PH  ", "02 03 02 03 00 01 75 81", "D/10", "", 9, 6, "0", "0"));
                sensors.add(new Sensor(4L, "865328063321359", 2, "土壤温度 ", "02 03 02 00 00 01 85 81", "D/100+5", "ºC", 25, 25, "0", "0"));
                sensors.add(new Sensor(5L, "865328063321359", 2, "土壤湿度 ", "02 03 02 01 00 01 D4 41", "D/100", "%", 100, 80, "0", "0"));
                sensors.add(new Sensor(6L, "865328063321359", 2, "土壤氮   ", "02 03 02 04 00 01 C4 40", "D/1", "mg/L", 100, 50, "0", "0"));
                sensors.add(new Sensor(7L, "865328063321359", 2, "土壤磷   ", "02 03 02 05 00 01 95 80", "D/1", "mg/L", 100, 50, "0", "0"));
                sensors.add(new Sensor(8L, "865328063321359", 2, "土壤钾   ", "02 03 02 06 00 01 65 80", "D/1", "mg/L", 100, 50, "0", "0"));
                sensors.add(new Sensor(9L, "865328063321359", 2, "土壤电导率", "02 03 02 02 00 01 24 41", "D/1", "us/cm", 250, 80, "0", "0"));
            }
            sensorDataArrMap.put(imei, sensors);
            //继电器指令集
            List<Relay> relays = relayDataArrMap.get(imei) == null ? new ArrayList<>() : relayDataArrMap.get(imei);
            if (relays.isEmpty()) {
                relays.add(new Relay(1L, "865328063321359", 3, "棚双锁开关", "03 05 00 00 FF 00 8D D8", "03 05 00 00 00 00 CC 28", 30000L, "lcaolhost:8080/hello", "棚双锁开关"));
                relays.add(new Relay(2L, "865328063321359", 3, "大棚总开关", "03 05 00 01 FF 00 DC 18", "03 05 00 01 00 00 9D E8", 30000L, "lcaolhost:8080/hello", "大棚总开关"));
                relays.add(new Relay(3L, "865328063321359", 3, "", "03 05 00 02 FF 00 2C 18", "03 05 00 02 00 00 6D E8", 0L, "lcaolhost:8080/hello", ""));
                relays.add(new Relay(4L, "865328063321359", 3, "", "03 05 00 03 FF 00 7D D8", "03 05 00 03 00 00 3C 28", 0L, "lcaolhost:8080/hello", ""));
            }
            relayDataArrMap.put(imei, relays);
            dtuInfo.setRelayList(relays);
            dtuInfo.setSensorList(sensors);
            dtuInfos.add(dtuInfo);
            dtuInfoMap.put(imei, dtuInfo);
            //todo 初始化relayStatusMap——查询继电器状态
            for (Relay relay : relays) {
                relayStatusMap.put(relay.getId(), 0);
            }
        }
        return sensorDataArrMap.get(imei);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // 获取当前连接的客户端的 channel
        Channel incoming = ctx.channel();
        // 将客户端的 Channel 存入 ChannelGroup 列表中
        channelGroup.add(incoming);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.getCause();
        ctx.channel().close();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) {
        Channel channel = ctx.channel();
        channelGroup.forEach(channel1 -> {
            if (channel1 == channel) {//匹配当前连接对象
                start(ctx, (ByteBuf) msg);
            }
        });

    }

    private void start(ChannelHandlerContext ctx, ByteBuf msg) {
        //当前数据个数
        ByteBuf byteBuf = msg;
        //获取缓冲区可读字节数
        int readableBytes = getReadableBytes(byteBuf);
        byte[] bytes = new byte[readableBytes];
        byteBuf.readBytes(bytes);
        //dtu必须开通注册功能，开通注册才可以查询到信息
        String imei = calculationImei(bytes);
        DtuInfo dtuInfo = dtuInfoMap.get(imei);
        if (readableBytes == registrationLength) {
            dtuRegister(bytes);
        } else if (readableBytes == (imeiNum + dtuInfo.getSensorLength())) { //处理dtu轮询返回值
            ProcessDtuPollingReturnValue(ctx, bytes);
        } else if (readableBytes == (imeiNum + dtuInfo.getRelayLength())) { //处理继电器返回值
            new Thread(() -> {
                processingRelayReturnValues(ctx, bytes);
            }).start();
        } else {
            log.error("获取的byte[]长度： {} ，不能解析数据,server received message：{}", readableBytes, HexConvert.BinaryToHexString(bytes));
        }
    }

    /**
     * 处理继电器返回值
     *
     * @param ctx
     * @param bytes
     */
    private void processingRelayReturnValues(ChannelHandlerContext ctx, byte[] bytes) {
        //把数据bytes转化为string
        String useData = sensorDataToString(bytes);
        //只检查闭合的接收数据，不检查断开的接收数据
        String imei = calculationImei(bytes);
        //处理继电器与发送有效指令返回值
        List<String> list = validInstructionsMap.get(imei);
        boolean b = list.contains(useData);
        if (!b) return;
        Optional<Relay> relayOptional = relayDataArrMap.get(imei).stream().filter(relay -> relay.getOpneHex().equals(useData) || relay.getCloseHex().equals(useData)).findFirst();
        if (relayOptional.isPresent()) {
            log.info("继电器==={}==成功！", relayOptional.get().getName());
            //如果不是自锁式的开关，则需要下面代码
            try {
                Thread.sleep(relayOptional.get().getAccessTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String hex = relayOptional.get().getOpneHex().equals(useData) ? relayOptional.get().getCloseHex() : relayOptional.get().getOpneHex();
            write(Arrays.asList(hex), ctx);
            //移除指令后再放后去——如果使用缓存失效就不用了
            list.remove(useData);
            validInstructionsMap.put(imei, list);
        }
    }

    /**
     * 处理dtu轮询返回值
     *
     * @param ctx
     * @param bytes
     */
    private void ProcessDtuPollingReturnValue(ChannelHandlerContext ctx, byte[] bytes) {
        collectSensorData(ctx, bytes);
        //计算imei
        String imei = calculationImei(bytes);
        //有效的数据后把最后一个时间记录为当前时间，否则一组有效信息永远不够
        endTimeMap.put(imei, LocalDateTime.now());
    }

    /**
     * 计算imei
     *
     * @param bytes
     * @return
     */
    private String calculationImei(byte[] bytes) {
        byte[] imeiBytes = new byte[imeiNum];
        System.arraycopy(bytes, 0, imeiBytes, 0, imeiNum);
        return HexConvert.hexStringToString(HexConvert.BinaryToHexString(imeiBytes).replaceAll(" ", ""));
    }

    /**
     * dtu向服务器注册
     *
     * @param bytes
     */
    private void dtuRegister(byte[] bytes) {
        log.info("==============dtu注册了=============");
        RegisterInfo registerInfo = null;
        try {
            registerInfo = objectMapper.readValue(new String(bytes, StandardCharsets.UTF_8), RegisterInfo.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String imei = registerInfo.getImei().trim();
        log.info("imei======={}", imei);
        //配置各参数
        setDeviceInformation(imei);
        //注册信息后把时间加上30秒——目的是为了第一次获取有效的数据
        endTimeMap.put(imei, LocalDateTime.now().minusSeconds(dtuInfoMap.get(imei).getGroupIntervalTime() + 30));
    }

    private int getReadableBytes(ByteBuf byteBuf) {
        return byteBuf.readableBytes();
    }


    /**
     * 传感器数据转为字符串——没有imei值的有效数据
     *
     * @param bytes 收到byte[]信息
     * @return
     */
    private String sensorDataToString(byte[] bytes) {
        String imei = calculationImei(bytes);
        //截取有效值进行分析——不要imei值
        int useDataLength = getUseDataLength(bytes);
        byte[] useBytes = new byte[useDataLength];
        System.arraycopy(bytes, dtuInfoMap.get(imei).getImeiLength(), useBytes, 0, useDataLength);
        return HexConvert.BinaryToHexString(useBytes).trim();
    }

    /**
     * 计算有用的长度
     *
     * @param bytes
     * @return
     */
    private int getUseDataLength(byte[] bytes) {
        int useDataLength = 0;
        DtuInfo dtuInfo = dtuInfoMap.get(calculationImei(bytes));
        if (bytes.length == (dtuInfo.getImeiLength() + dtuInfo.getSensorLength())) {
            useDataLength = dtuInfo.getSensorLength();
        }
        if (bytes.length == (dtuInfo.getImeiLength() + dtuInfo.getRelayLength())) {
            useDataLength = dtuInfo.getRelayLength();
        }
        return useDataLength;
    }

    /**
     * 收集感应器数据
     *
     * @param ctx   通道上下文
     * @param bytes 收到byte[]信息
     */
    private void collectSensorData(ChannelHandlerContext ctx, byte[] bytes) {
        String imei = calculationImei(bytes);
        int sensorsLength = sensorDataArrMap.get(imei).size();
        //计算当前时间与之前时间差值
        LocalDateTime end = endTimeMap.get(imei);
        Duration duration = Duration.between(end, LocalDateTime.now());
        long millis = duration.toMillis();
        List<byte[]> sensorDataByteList;
        if (millis >= dtuInfoMap.get(imei).getGroupIntervalTime()) {
            log.info("==========查询一组出数据===========");
            sensorDataByteList = new ArrayList<>(sensorsLength);
            sensorDataByteList.add(bytes);
            sensorDataByteListMap.put(imei, sensorDataByteList);
        } else {
            sensorDataByteList = sensorDataByteListMap.get(imei);
            sensorDataByteList.add(bytes);
            sensorDataByteListMap.put(imei, sensorDataByteList);
        }
        if (sensorDataByteListMap.get(imei).size() == sensorsLength) {
            log.info("==========解析一组传感器的有用数据===========");
            List<SensorData> sensorDataList = parseSensorListData(sensorDataByteListMap.get(imei), ctx);
            //插入数据库
            insertDatabase(imei, sensorDataList);
        }
    }

    /**
     * 添加到数据库中
     *
     * @param imei
     * @param sensorDataList
     */
    private void insertDatabase(String imei, List<SensorData> sensorDataList) {
        List<String> nameList = sensorDataList.stream().map(sensorData -> sensorData.getName().trim()).collect(Collectors.toList());
        String names = StringUtils.join(nameList, ",");
        List<String> dataList = sensorDataList.stream().map(sensorData -> sensorData.getData().toString()).collect(Collectors.toList());
        String data = StringUtils.join(dataList, ",");
        List<String> unitList = sensorDataList.stream().map(sensorData -> sensorData.getUnit()).collect(Collectors.toList());
        String units = StringUtils.join(unitList, ",");
        // TODO: 2023/1/10 改为jpa存数据
//        LinkedHashMap<String, String> params = new LinkedHashMap<>();
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String format = sf.format(new Date());
//        params.put("createDate", format);
//        params.put("imei", imei);
//        params.put("names", names);
//        params.put("data", data);
//        params.put("units", units);
//        Dbcp2Base.execute(1, "insert into sensor_data(createDate,imei,names,data,units) values(?,?,?,?,?)", params, SensorDataDb.class);
    }

    /**
     * 批量解析收到的数据
     *
     * @param list
     * @param ctx  通道上下文
     * @return
     */
    private List<SensorData> parseSensorListData(List<byte[]> list, ChannelHandlerContext ctx) {
        List<SensorData> doubleList = new ArrayList<>();
        //按顺序解析，根据sensor顺序解析找对应关系
        for (int i = 0; i < list.size(); i++) {
            Double aDouble = parseSensorOneData(list.get(i), i, ctx);
            String imei = calculationImei(list.get(0));
            List<Sensor> sensors = sensorDataArrMap.get(imei);
            Sensor sensor = sensors.get(i);
            SensorData sensorData = new SensorData(i, sensor.getName(), aDouble, sensor.getUnit());
            doubleList.add(sensorData);
        }
        return doubleList;
    }


    /**
     * 向dtu发送指令
     *
     * @param hexs
     * @param ctx  通道上下文
     */
    private void write(final List<String> hexs, ChannelHandlerContext ctx) {
        //加锁，查询和继电指令相互交叉
        synchronized (this) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (String hex : hexs) {
                //netty需要用ByteBuf传输
                ByteBuf bufff = Unpooled.buffer();
                //对接需要16进制的byte[],不需要16进制字符串有空格
                bufff.writeBytes(HexConvert.hexStringToBytes(hex.replaceAll(" ", "")));
                ctx.writeAndFlush(bufff);
            }
        }

    }

    /**
     * 传感器接收数据解析——温湿度和土壤一样规则
     *
     * @param bytes       收到byte[]信息——22长度
     * @param arrayNumber 数组编号
     * @param ctx         通道上下文
     */
    private Double parseSensorOneData(byte[] bytes, int arrayNumber, ChannelHandlerContext ctx) {
        ////有用的bytes[]的值
        int useBytesLength = getUseDataLength(bytes);
        byte[] useBytes = new byte[useBytesLength];
        System.arraycopy(bytes, imeiNum, useBytes, 0, useBytesLength);  //数组截取
        //crc16校验
        boolean validCrc = validCRC16(useBytes);
        //CRC16验证通过数据才可以使用
        if (validCrc) {
            String hex = "0x" + HexConvert.BinaryToHexString(useBytes).substring(9, 14).replace(" ", "");
            Integer x = Integer.parseInt(hex.substring(2), 16);//从第2个字符开始截取
            //获取数据值
            double d = Double.parseDouble(String.valueOf(x));
            String imei = calculationImei(bytes);
            Sensor sensor = sensorDataArrMap.get(imei).get(arrayNumber);
            //经过公式计算得到实际结果
            Double actualResults = calculateActualData(sensor.getCalculationFormula(), d);
            log.info(" {} =====> {}  ====> {}  ====> {}", arrayNumber, sensor.getAdrss(), sensor.getName(), actualResults + sensor.getUnit());
            //开新建程——异步处理根据解析到数据大小判断是否产生事件
            if (dtuInfoMap.get(calculationImei(bytes)).getAutomaticAdjustment()) {
                new Thread(() -> {
                    criteria(sensor, actualResults, ctx);
                }).start();
            }
            return actualResults;
        }
        return null;
    }


    /**
     * crc16校验——校验7位bytes,最后两位为校验为
     *
     * @param bytes 收到byte[]信息
     * @return
     */
    private boolean validCRC16(byte[] bytes) {
        //传感器地址
        String[] s = HexConvert.BinaryToHexString(bytes).split(" ");
        String str = "";
        for (int i = 5; i < s.length; i++) {
            str = str + s[i];
        }
        //有效字符串
        byte[] bytes2 = new byte[5];
        System.arraycopy(bytes, 0, bytes2, 0, 5);  //数组截取
        return CRC16.getCRC3(bytes2).equalsIgnoreCase(str);
    }

    /**
     * 判断处理
     *
     * @param sensor 感应器指令
     * @param data   测试结果值
     * @param ctx    通道上下文
     */
    private void criteria(Sensor sensor, double data, ChannelHandlerContext ctx) {
        String ids = "";
        //根据结果值判断是否处理
        if (data > sensor.getMax()) {
            log.info("{} 结果值 {} 大于最大值 {}", sensor.getName(), data, sensor.getMax());
            ids = sensor.getMaxControIds();
        } else if (data < sensor.getMin()) {
            log.info("{} 结果值 {} 小于最小值{}", sensor.getName(), data, sensor.getMin());
            ids = sensor.getMinControIds();
        } else {
            log.info("{} 结果值 {} 比较合理，不用处理！", sensor.getName(), data);
        }
        relayCommandData(sensor, ids, ctx);
    }


    /**
     * 根据数据处理继电器指令处理
     *
     * @param sensor 串号
     * @param ids    继电器指令——奇数表示对应的继电器id，偶数：1表示为使用闭合指令，0表示为断开指令
     * @param ctx    通道上下文
     */
    private void relayCommandData(Sensor sensor, String ids, ChannelHandlerContext ctx) {
        if (ids.equals("0")) return;
        //根据imei查询所有继电器指令:
        List<Relay> relayList = relayDataArrMap.get(sensor.getImei());
        //指令奇数表示对应的继电器id，偶数：1表示为使用闭合指令，0表示为断开指令
        String[] split = ids.split(",");
        //排序
//        Arrays.sort(split);
        for (String s : split) {
            String[] split1 = s.split("-");
            for (Relay relay : relayList) {
                if (String.valueOf(relay.getId()).equals(split1[0])) {
                    String sendHex = split1[1].equals("1") ? relay.getOpneHex() : relay.getCloseHex();
                    log.info("发送imei值：{} ,继电器为：{}，指令为：{}", sensor.getImei(), split1[1].equals("1") ? "闭合指令" : "断开指令", sendHex);
                    write(Arrays.asList(sendHex), ctx);
                    //添加到要处理的有效指令中——在处理完后删除
                    List<String> list = validInstructionsMap.get(sensor.getImei()) == null ? new ArrayList<>() : validInstructionsMap.get(sensor.getImei());
                    list.add(sendHex);
                    validInstructionsMap.put(sensor.getImei(), list);
                    // TODO: 2023/1/4 处理url发出指令
                    break;
                }
            }
        }
    }

    /**
     * 计算实际数据
     *
     * @param formula     公式
     * @param measureData 测量数据
     * @return
     */
    public Double calculateActualData(String formula, double measureData) {
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
}