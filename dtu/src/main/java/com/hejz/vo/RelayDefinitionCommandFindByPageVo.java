//package com.hejz.vo;
//
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//
///**
// * @author:hejz 75412985@qq.com
// * @create: 2023-01-31 21:29
// * @Description:
// */
//@Data
//public class RelayDefinitionCommandFindByPageVo {
//    @ApiModelProperty(value = "ID")
//    private Long id;
//    @ApiModelProperty(value = "dtuId")
//    private Long dtuId;
//    @ApiModelProperty(value = "名称")
//    private String name;
//    @ApiModelProperty(value = "备注")
//    private String remarks;
//    @ApiModelProperty(value = "继电器指令——以间隔号隔开，前面是继电器id值，后面则是1为闭合指令0为断开指令")
//    private String relayIds;
//    @ApiModelProperty(value = "是否处理返回值——false表示不用，true表示必须处理")
//    private Boolean isProcessTheReturnValue= false;
//    @ApiModelProperty(value = "处理等待时间（毫秒）——对继电器命令进行归零操作")
//    private Long processingWaitingTime;
//    @ApiModelProperty(value = "处理返回值要使用继电器ids——在此类表中查询,0表示没有")
//    private Long commonId =0L;
//    @ApiModelProperty(value = "相对应的命令id")
//    private Long correspondingCommandId;
//}
