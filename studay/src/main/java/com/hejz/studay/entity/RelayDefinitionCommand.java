package com.hejz.studay.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-11 08:04
 * @Description: 继电器定义指令
 */
@Data
@Entity(name = "relay_definition_command")
@NoArgsConstructor
@AllArgsConstructor
public class RelayDefinitionCommand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //ID
    @Column(
            name = "id",
            nullable = false,
            columnDefinition="bigint"+" COMMENT 'ID'"
    )
    private Long id;
    //名称
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
            columnDefinition="varchar(255)"+" COMMENT '继电器ids'"
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
            name = "process_the_return_value_time",
            nullable = true,
            columnDefinition="bigint"+" COMMENT '处理返回值间隔时间（毫秒）——对继电器命令进行归零操作'"
    )
    private Long processTheReturnValueTime;
    //处理返回值要使用继电器ids
    @Column(
            name = "return_relay_ids",
            nullable = true,
            columnDefinition="varchar(255)"+" COMMENT '处理返回值要使用继电器ids'"
    )
    private String returnRelayIds;

    public RelayDefinitionCommand(String name, String remarks,String relayIds, boolean isProcessTheReturnValue, long processTheReturnValueTime, String returnRelayIds) {
        this.name=name;
        this.remarks=remarks;
        this.relayIds=relayIds;
        this.isProcessTheReturnValue=isProcessTheReturnValue;
        this.processTheReturnValueTime=processTheReturnValueTime;
        this.returnRelayIds=returnRelayIds;
    }
}
