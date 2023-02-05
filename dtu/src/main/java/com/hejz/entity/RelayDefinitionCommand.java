package com.hejz.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hejz.enm.InstructionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-11 08:04
 * @Description: 继电器定义指令
 */
@Data
@Entity(name = "relay_definition_command")
@org.hibernate.annotations.Table(appliesTo = "relay_definition_command", comment = "继电器定义指令")
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
    @Column(
            name = "dtu_id",
            nullable = false,
            columnDefinition="bigint"+" COMMENT 'dtuId'"
    )
    private Long dtuId;
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
    //处理返回值间隔时间（毫秒）——对继电器命令进行归零操作
    @Column(
            name = "processing_waiting_time",
            nullable = true,
            columnDefinition="bigint"+" COMMENT '处理等待时间（毫秒）——对继电器命令进行归零操作'"
    )
    private Long processingWaitingTime;
    //处理返回值要使用继电器ids
    @Column(
            name = "next_level_instruction",
            nullable = true,
            columnDefinition="bigint default 0"+" COMMENT '下一级指令'"
    )
    private Long nextLevelInstruction =0L;
    @Column(
            name = "corresponding_command_id",
            nullable = true,
            columnDefinition="bigint default 0"+" COMMENT '相对应的命令id'"
    )
    private Long correspondingCommandId;
    @Column(
            name = "instruction_type_enum",
            nullable = false,
            columnDefinition="int(2)"+" COMMENT '指令类型枚举'"
    )
    private InstructionTypeEnum instructionTypeEnum;
    //发送指令时间，超过1小时不再发送指令了
    @Transient
    private LocalDateTime sendCommandTime;


    public RelayDefinitionCommand(Long dtuId, String name, String remarks, String relayIds, long processingWaitingTime, Long nextLevelInstruction, Long correspondingCommandId, InstructionTypeEnum instructionTypeEnum) {
        this.dtuId=dtuId;
        this.name=name;
        this.remarks=remarks;
        this.relayIds=relayIds;
        this.processingWaitingTime = processingWaitingTime;
        this.nextLevelInstruction = nextLevelInstruction;
        this.correspondingCommandId = correspondingCommandId;
        this.instructionTypeEnum = instructionTypeEnum;

    }
}
