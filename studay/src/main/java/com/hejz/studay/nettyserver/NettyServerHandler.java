package com.hejz.studay.nettyserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hejz.studay.entity.*;
import com.hejz.studay.service.*;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.script.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
@ChannelHandler.Sharable
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler {
    //IMEI长度
    public static final int IMEI_LENGTH = 15;
    //dut注册bytes长度
    public static final int DUT_REGISTERED_BYTES_LENGTH = 89;
    //DTU轮询返回长度
    public static final int DTU_POLLING_RETURN_LENGTH = 22;
    //继电器返回值长度
    public static final int RELAY_RETURN_VALUES_LENGTH = 23;
    //间隔时间
    public static final int INTERVAL_TIME = 50;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    SensorDataDbService sensorDataDbService;
    @Autowired
    RelayService relayService;
    @Autowired
    SensorService sensorService;
    @Autowired
    DtuInfoService dtuInfoService;
    @Autowired
    RelayDefinitionCommandService relayDefinitionCommandService;
    @Autowired
    DataCheckingRulesService dataCheckingRulesService;
    @Autowired
    RedisTemplate redisTemplate;
    //所有的连接
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static final Map<String, LocalDateTime> endTimeMap = new HashMap<>();
    //缓存每组dtu查询后返回的bytes值，够数量才解析，不够数量解析没有用
    private static final Map<String, List<byte[]>> sensorDataByteListMap = new HashMap<>();
    //记录当前iemi中的dtuInfo信息
//    private static final Map<String, DtuInfo> dtuInfoMap = new HashMap<>();
    //继电器状态值记录
    private static final Map<Long, Integer> relayStatusMap = new HashMap<>();
    //处理器指令集


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
        if (readableBytes == DUT_REGISTERED_BYTES_LENGTH) {
            dtuRegister(bytes);
        } else if (readableBytes == (DTU_POLLING_RETURN_LENGTH)) { //处理dtu轮询返回值
            ProcessDtuPollingReturnValue(ctx, bytes);
        } else if (readableBytes == (RELAY_RETURN_VALUES_LENGTH)) { //处理继电器返回值
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
        String imei = calculationImei(bytes);
        //把数据bytes转化为string
        String useData = sensorDataToString(bytes);
        log.info("继电器返回值：{}", useData);
        if(!testingData(bytes)){
            log.error("继电器返回值：{}校验不通过！",HexConvert.BinaryToHexString(bytes));
        }
        //只检查闭合的接收数据，不检查断开的接收数据
        //查询机电器指令与之相配
        Optional<Relay> relayOptional = relayService.getByImei(imei).stream().filter(relay -> relay.getOpneHex().equals(useData) || relay.getCloseHex().equals(useData)).findFirst();
        if (!relayOptional.isPresent()) return;
        int hexStatus = 0;
        if (relayOptional.get().getOpneHex().equals(useData)) hexStatus = 1;
        String ids = relayOptional.get().getId() + "-" + hexStatus;
        List<RelayDefinitionCommand> relayDefinitionCommands = relayDefinitionCommandService.getByImei(imei).stream().filter(r -> r.getRelayIds().indexOf(ids) >= 0 && r.getIsProcessTheReturnValue()).collect(Collectors.toList());
        if (relayDefinitionCommands.isEmpty()) return;
        LinkedHashSet<RelayDefinitionCommand> relayDefinitionCommandList = new LinkedHashSet<>();
        for (RelayDefinitionCommand relayDefinitionCommand : relayDefinitionCommands) {
            Optional<RelayDefinitionCommand> first = relayDefinitionCommandService.getByImei(imei).stream().filter(r -> r.getId().equals(relayDefinitionCommand.getCommonId())).findFirst();
            if (first.isPresent()) relayDefinitionCommandList.add(first.get());
        }
        List<String> sendHexs = getSendHex(relayService.getByImei(imei), relayDefinitionCommandList);
        try {
            Thread.sleep(relayDefinitionCommands.get(0).getProcessTheReturnValueTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (String sendHex : sendHexs) {
            write(sendHex, ctx, imei);
        }
    }

    /**
     * 继电器指令返回数据处理
     *
     * @param relays
     * @param relayDefinitionCommands
     * @return
     */
    private List<String> getSendHex(List<Relay> relays, LinkedHashSet<RelayDefinitionCommand> relayDefinitionCommands) {
        List<String> relayIds = relayDefinitionCommands.stream().map(RelayDefinitionCommand::getRelayIds).collect(Collectors.toList());
        List<String> relayIdList = new ArrayList<>();
        for (String relayId : relayIds) {
            relayIdList.addAll(Arrays.asList(relayId.split(",")));
        }
        List result = new ArrayList();
        for (String s : relayIdList) {
            String[] strings = s.split("-");
            List<String> list = relays.stream().filter(r -> r.getId().equals(Long.valueOf(strings[0])))
                    .map(strings[1].equals("1") ? Relay::getOpneHex : Relay::getCloseHex)
                    .collect(Collectors.toList());
            result.addAll(list);
        }
        return result;
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
        byte[] imeiBytes = new byte[IMEI_LENGTH];
        System.arraycopy(bytes, 0, imeiBytes, 0, IMEI_LENGTH);
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
        //注册信息后把时间加上30秒——目的是为了第一次获取有效的数据
        endTimeMap.put(imei, LocalDateTime.now().minusSeconds(INTERVAL_TIME + 30));
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
        //截取有效值进行分析——不要imei值
        int useLength = bytes.length-IMEI_LENGTH;
        byte[] useBytes = getUseBytes(bytes,useLength);
        return HexConvert.BinaryToHexString(useBytes).trim();
    }


    /**
     * 收集感应器数据
     *
     * @param ctx   通道上下文
     * @param bytes 收到byte[]信息
     */
    private void collectSensorData(ChannelHandlerContext ctx, byte[] bytes) {
        String imei = calculationImei(bytes);
        int sensorsLength = sensorService.getByImei(imei).size();
        //计算当前时间与之前时间差值
        LocalDateTime end = endTimeMap.get(imei);
        Duration duration = Duration.between(end, LocalDateTime.now());
        long millis = duration.toMillis();
        List<byte[]> sensorDataByteList;
        //必须检测是有用的数据才可以，如果不能够使用才不可以
        if (!testingData(bytes)) return;
        if (millis >= dtuInfoService.getByImei(imei).getGroupIntervalTime()) {
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
     * 必须检测是有用的数据才可以，如果不能够使用才不可以
     *
     * @param bytes
     * @return
     */
    private boolean testingData(byte[] bytes) {
        //1、必须注册过的imei值——查dtuInfo看有没有
        String imei = calculationImei(bytes);
        DtuInfo dtuInfo = dtuInfoService.getByImei(imei);
        if (dtuInfo == null) {
            log.error("bytes：{}校验通不过——查无imei值：{}", HexConvert.BinaryToHexString(bytes), imei);
            return false;
        }
        //todo 2、数据校检规则校验
        int bytesLength = bytes.length - IMEI_LENGTH;
        //查出所有符合此长度的规则
        List<DataCheckingRules> dataCheckingRules = dataCheckingRulesService.getByCommonLength(bytesLength);
        for (DataCheckingRules dataCheckingRule : dataCheckingRules) {
            //使用crc16校验——不需要imei值
            Integer useLength = dataCheckingRule.getCommonLength();
            byte[] useBytes = getUseBytes(bytes, useLength);
            boolean b = validCRC16(useBytes, dataCheckingRule);
            if (!b) {
                log.error("bytes：{}校验通不过——crc16校验不过：{}", HexConvert.BinaryToHexString(bytes));
                return false;
            }
            //todo 地址位不在dtuInfo中
        }
        return true;
    }

    /**
     * 截取有用的bytes——不含imei
     * @param bytes
     * @param useLength
     * @return
     */
    private byte[] getUseBytes(byte[] bytes, Integer useLength) {
        byte[] useBytes = new byte[useLength];
        System.arraycopy(bytes, IMEI_LENGTH, useBytes, 0, useLength);  //数组截取
        return useBytes;
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
        //存进数据
        SensorDataDb sensorDataDb = new SensorDataDb(new Date(), imei, names, data, units);
        sensorDataDbService.save(sensorDataDb);
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
            List<Sensor> sensors = sensorService.getByImei(imei);
            Sensor sensor = sensors.get(i);
            SensorData sensorData = new SensorData(i, sensor.getName(), aDouble, sensor.getUnit());
            doubleList.add(sensorData);
        }
        return doubleList;
    }


    /**
     * 向dtu发送指令
     *
     * @param hex
     * @param ctx  通道上下文
     * @param imei
     */
    private void write(final String hex, ChannelHandlerContext ctx, String imei) {
        //重复指令一个轮询周期只发一次
        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(imei + ":" + hex, hex, Duration.ofMillis(dtuInfoService.getByImei(imei).getGroupIntervalTime()));
        if (!aBoolean) return;
        log.info("发送指令：{}", hex);
        //加锁，查询和继电指令相互交叉
        synchronized (this) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //netty需要用ByteBuf传输
            ByteBuf bufff = Unpooled.buffer();
            //对接需要16进制的byte[],不需要16进制字符串有空格
            bufff.writeBytes(HexConvert.hexStringToBytes(hex.replaceAll(" ", "")));
            ctx.writeAndFlush(bufff);
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
        int useLength = bytes.length-IMEI_LENGTH;
        log.info("收到16进制数据：{}", HexConvert.BinaryToHexString(bytes));
        byte[] useBytes = getUseBytes(bytes,useLength);
        String hex = "0x" + HexConvert.BinaryToHexString(useBytes).substring(9, 14).replace(" ", "");
        Integer x = Integer.parseInt(hex.substring(2), 16);//从第2个字符开始截取
        //获取数据值
        double d = Double.parseDouble(String.valueOf(x));
        String imei = calculationImei(bytes);
        Sensor sensor = sensorService.getByImei(imei).get(arrayNumber);
        //经过公式计算得到实际结果
        Double actualResults = calculateActualData(sensor.getCalculationFormula(), d);
        log.info(" {} =====> {}  ====> {}  ====> {}", arrayNumber, sensor.getAdrss(), sensor.getName(), actualResults + sensor.getUnit());
        //开新建程——异步处理根据解析到数据大小判断是否产生事件
        if (dtuInfoService.getByImei(calculationImei(bytes)).getAutomaticAdjustment()) {
            new Thread(() -> {
                criteria(sensor, actualResults, ctx);
            }).start();
        }
        return actualResults;
    }


    /**
     * crc16校验——校验7位bytes,最后两位为校验为
     *
     * @param bytes            收到byte[]信息
     * @param dataCheckingRule
     * @return
     */
    private boolean validCRC16(byte[] bytes, DataCheckingRules dataCheckingRule) {
        //传感器地址
        String[] s = HexConvert.BinaryToHexString(bytes).split(" ");
        //校验位字符
        String checkDigitStr = "";
        ////有效字符串长度
        int useLength = dataCheckingRule.getCommonLength() - dataCheckingRule.getCrc16CheckDigitLength();
        for (int i = useLength; i < s.length; i++) {
            checkDigitStr = checkDigitStr + s[i];
        }
        //有效字符串
        byte[] useBytes = new byte[useLength];
        System.arraycopy(bytes, 0, useBytes, 0, useLength);  //数组截取
        return CRC16.getCRC3(useBytes).equalsIgnoreCase(checkDigitStr);
    }

    /**
     * 判断处理
     *
     * @param sensor 感应器指令
     * @param data   测试结果值
     * @param ctx    通道上下文
     */
    private void criteria(Sensor sensor, double data, ChannelHandlerContext ctx) {
        Long id = null;
        //根据结果值判断是否处理
        if (data > sensor.getMax()) {
            log.info("{} 结果值 {} 大于最大值 {}", sensor.getName(), data, sensor.getMax());
            id = sensor.getMaxRelayDefinitionCommandId();
        } else if (data < sensor.getMin()) {
            log.info("{} 结果值 {} 小于最小值{}", sensor.getName(), data, sensor.getMin());
            id = sensor.getMinRelayDefinitionCommandId();
        } else {
            log.info("{} 结果值 {} 比较合理，不用处理！", sensor.getName(), data);
        }
        relayCommandData(sensor, id, ctx);
    }


    /**
     * 根据数据处理继电器指令处理
     *
     * @param sensor 串号
     * @param id     继电器指令——奇数表示对应的继电器id，偶数：1表示为使用闭合指令，0表示为断开指令
     * @param ctx    通道上下文
     */
    private void relayCommandData(Sensor sensor, Long id, ChannelHandlerContext ctx) {
        if (id == null || id.equals("0")) return;
        //编辑继电器指令
        Optional<RelayDefinitionCommand> first = relayDefinitionCommandService.getByImei(sensor.getImei()).stream().filter(relayDefinitionCommand -> relayDefinitionCommand.getId().equals(id)).findFirst();
        if (!first.isPresent()) return;
        log.info("===============正在执行指令：{}",first.get().getName());
        String relayIds = first.get().getRelayIds();
        //根据imei查询所有继电器指令:
        List<Relay> relayList = relayService.getByImei(sensor.getImei());
        //指令奇数表示对应的继电器id，偶数：1表示为使用闭合指令，0表示为断开指令
        String[] split = relayIds.split(",");
        for (String s : split) {
            String[] split1 = s.split("-");
            for (Relay relay : relayList) {
                if (String.valueOf(relay.getId()).equals(split1[0])) {
                    String sendHex = split1[1].equals("1") ? relay.getOpneHex() : relay.getCloseHex();
//                    log.info("发送imei值：{} ,继电器id：{}-{}，指令为：{}", sensor.getImei(),relay.getId(), split1[1].equals("1") ? "闭合指令" : "断开指令", sendHex);
                    write(sendHex, ctx, sensor.getImei());
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