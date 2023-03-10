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
@Entity(name = "instruction_definition_status")
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Table(appliesTo = "instruction_definition_status", comment = "继电器命令状态")
public class InstructionDefinitionStatus implements Serializable{

    @Id
    @SequenceGenerator(
            name = "instruction_definition_status_sequence",
            sequenceName = "instruction_definition_status_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "instruction_definition_status_sequence"
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
            columnDefinition="datetime"+" COMMENT '创建时间'"
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
            columnDefinition="datetime"+" COMMENT '修改时间'"
    )
    private java.util.Date updateDate;
    /**
     * 外键表——instruction_definition中的字段id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "instruction_definition_id")
    @JsonIgnoreProperties(value = {"instructionDefinitionStatus"})
    private InstructionDefinition instructionDefinition;
    /**
     * 外键表——tb_dtu_info中的字段id，此字段是为了查询方便，保存时InstructionDefinition已经含有了dtuId
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"instructionDefinitionStatus"})
    @JoinColumn(name = "dtu_id")
    private DtuInfo dtuInfo;
}
