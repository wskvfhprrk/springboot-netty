package com.hejz.dtu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * 继电器命令状态实体类
 * author: hejz
 * data: 2023-2-7
 */
@Data
@Entity(name = "command_status")
@NoArgsConstructor
@AllArgsConstructor
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
            columnDefinition="bigint"+" COMMENT '继电器命令状态ID'"
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
    /**
     * 外键表——instruction_definition中的字段id
     */
    @ManyToOne
    @JoinColumn(name = "instruction_definition_id",insertable = false,updatable = false)
    private InstructionDefinition instructionDefinition;
    /**
     * 外键表——tb_dtu_info中的字段id
     */
    @ManyToOne
    @JoinColumn(name = "dtu_id",insertable = false,updatable = false)
    private DtuInfo dtuInfo;

    public CommandStatus(DtuInfo dtuInfo, InstructionDefinition instructionDefinition, Date createDate, Date updateDate, boolean status) {
        this.dtuInfo=dtuInfo;
        this.instructionDefinition=instructionDefinition;
        this.createDate=createDate;
        this.updateDate=updateDate;
        this.status=status;

    }
}
