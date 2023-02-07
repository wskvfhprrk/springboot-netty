package com.hejz.dtu.entity;

import lombok.Data;
import java.io.Serializable;
import javax.persistence.*;

/**
 * 继电器命令状态实体类
 * author: hejz
 * data: 2023-2-7
 */
@Data
@Entity(name = "command_status")
@org.hibernate.annotations.Table(appliesTo = "command_status", comment = "继电器命令状态")
public class CommandStatus implements Serializable{

    @Id
    @SequenceGenerator(
            name = "command_status_sequence",
            sequenceName = "command_status_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "command_status_sequence"
    )
    @Column(
            name = "id",
            nullable = false,
            columnDefinition="bigint"+" COMMENT 'ID'"
    )
    private Long id;

    @Column(
            name = "create_date",
            nullable = false,
            columnDefinition="date"+" COMMENT '创建时间'"
    )
    private java.util.Date createDate;

    @Column(
            name = "status",
            nullable = false,
            columnDefinition="date"+" COMMENT '当前状态——true是新的状态，false是过期的状态'"
    )
    private Boolean status;

    @Column(
            name = "update_date",
            nullable = false,
            columnDefinition="date"+" COMMENT '修改时间'"
    )
    private java.util.Date updateDate;

    @Column(
            name = "dtu_id",
            nullable = false,
            columnDefinition="bigint"+" COMMENT 'ID'"
    )
    private Long dtuId;

    @Column(
            name = "instruction_definition_id",
            nullable = false,
            columnDefinition="bigint"+" COMMENT 'ID'"
    )
    private Long instructionDefinitionId;
    /**
     * 外键表——instruction_definition中的字段id
     */
    @ManyToOne
    @JoinColumn(name = "instruction_definition_id",insertable = false,updatable = false)
    private InstructionDefinition InstructionDefinition;
    /**
     * 外键表——tb_dtu_info中的字段id
     */
    @ManyToOne
    @JoinColumn(name = "dtu_id",insertable = false,updatable = false)
    private DtuInfo DtuInfo;
}
