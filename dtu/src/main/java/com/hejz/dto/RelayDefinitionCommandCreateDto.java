//package com.hejz.dto;
//
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//
//import javax.validation.constraints.NotEmpty;
//
//
//@Data
//@ApiModel
//public class RelayDefinitionCommandCreateDto {
//    @ApiModelProperty(value = "处理返回值要使用继电器ids——在此类表中查询,0表示没有")
//    private String commonId;
//    @ApiModelProperty(value = "相对应的命令id")
//    private Long correspondingCommandId;
//    @ApiModelProperty(value = "dtuId",required = true)
//    @NotEmpty
//    private Long dtuId;
//    @ApiModelProperty(value = "指令类型枚举",required = true,example = "22")
//    @NotEmpty
//    private Integer instructionTypeEnum;
//    @ApiModelProperty(value = "是否处理返回值——false表示不用，true表示必须处理",required = true,example = "22")
//    @NotEmpty
//    private Integer isProcessTheReturnValue;
//    @ApiModelProperty(value = "名称",required = true)
//    @NotEmpty
//    private String name;
//    @ApiModelProperty(value = "处理等待时间（毫秒）——对继电器命令进行归零操作")
//    private Long processingWaitingTime;
//    @ApiModelProperty(value = "继电器指令——以间隔号隔开，前面是继电器id值，后面则是1为闭合指令0为断开指令",required = true)
//    @NotEmpty
//    private String relayIds;
//    @ApiModelProperty(value = "备注")
//    private String remarks;
//}
