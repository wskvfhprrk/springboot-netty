package com.hejz.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-11 08:04
 * @Description: 继电器定义指令
 */
@Data
@Entity(name = "relay_definition_command")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class RelayDefinitionCommand implements Serializable {
    @Id
    @SequenceGenerator(
            name = "relay_definition_command_sequence",
            sequenceName = "relay_definition_command_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "relay_definition_command_sequence"
    )
    //ID
    @Column(
            name = "id",
            nullable = false,
            columnDefinition="bigint"+" COMMENT 'ID'"
    )
    private Long id;
    //名称
    @Column(
            name = "imei",
            nullable = false,
            columnDefinition="varchar(15)"+" COMMENT 'imei'"
    )
    private String imei;
    @Column(
            name = "name",
            nullable = false,
            columnDefinition="varchar(255)"+" COMMENT '名称'"
    )
    private String name;
    //备注
    @Column(
            name = "remarks",
            nullable = true,
            columnDefinition="varchar(255)"+" COMMENT '备注'"
    )
    private String remarks;
    //继电器ids
    @Column(
            name = "relay_ids",
            nullable = false,
            columnDefinition="varchar(255)"+" COMMENT '继电器指令——以间隔号隔开，前面是继电器id值，后面则是1为闭合指令0为断开指令'"
    )
    private String relayIds;
    //是否处理返回值——false表示不用，true表示必须处理
    @Column(
            name = "is_process_the_return_value",
            nullable = false,
            columnDefinition="bit"+" COMMENT '是否处理返回值——false表示不用，true表示必须处理'"
    )
    private Boolean isProcessTheReturnValue= false;
    //处理返回值间隔时间（毫秒）——对继电器命令进行归零操作
    @Column(
            name = "processing_waiting_time",
            nullable = true,
            columnDefinition="bigint"+" COMMENT '处理等待时间（毫秒）——对继电器命令进行归零操作'"
    )
    private Long processingWaitingTime;
    //处理返回值要使用继电器ids
    @Column(
            name = "common_id",
            nullable = true,
            columnDefinition="varchar(255) default 0"+" COMMENT '处理返回值要使用继电器ids——在此类表中查询,0表示没有'"
    )
    private Long commonId =0L;
    @Column(
            name = "corresponding_command_id",
            nullable = true,
            columnDefinition="bigint default 0"+" COMMENT '相对应的命令id'"
    )
    private Long correspondingCommandId;


    public RelayDefinitionCommand(String imei, String name, String remarks, String relayIds, boolean isProcessTheReturnValue, long processingWaitingTime, Long commonId, Long correspondingCommandId) {
        this.imei=imei;
        this.name=name;
        this.remarks=remarks;
        this.relayIds=relayIds;
        this.isProcessTheReturnValue=isProcessTheReturnValue;
        this.processingWaitingTime = processingWaitingTime;
        this.commonId = commonId;
        this.correspondingCommandId = correspondingCommandId;

    }
}
