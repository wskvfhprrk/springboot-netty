package com.hejz.dtu.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * 继电器命令状态实体类
 * author: hejz
 * data: 2023-2-7
 */
@Getter
@Setter
@Entity(name = "equ_command_status")
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(appliesTo = "equ_command_status", comment = "继电器命令状态")
public class InstructionDefinitionStatus implements Serializable{

    @Id
    @SequenceGenerator(
            name = "equ_command_status_sequence",
            sequenceName = "equ_command_status_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "equ_command_status_sequence"
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
            columnDefinition="bit"+" COMMENT '当前状态——true是新的状态，false是过期的状态'"
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instruction_definition_id")
    @JsonIgnoreProperties(value = {"commandStatus"})
    private InstructionDefinition instructionDefinition;
    /**
     * 外键表——tb_dtu_info中的字段id，此字段是为了查询方便，保存时InstructionDefinition已经含有了dtuId
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"commandStatus"})
    @JoinColumn(name = "dtu_id")
    private DtuInfo dtuInfo;

    public InstructionDefinitionStatus(DtuInfo dtuInfo, InstructionDefinition instructionDefinition, Date createDate, Date updateDate, boolean status) {
        this.dtuInfo=dtuInfo;
        this.instructionDefinition=instructionDefinition;
        this.createDate=createDate;
        this.updateDate=updateDate;
        this.status=status;

    }
}
