package com.hejz.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-14 07:46
 * @Description: 继电器命令状态
 */
@Data
@Entity(name = "command_status")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
@NoArgsConstructor
@AllArgsConstructor
public class CommandStatus implements Serializable {
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
            name = "imei",
            nullable = false,
            columnDefinition="varchar(15)"+" COMMENT 'imei'"
    )
    private String imei;
    @Column(
            name = "common_id",
            nullable = false,
            columnDefinition="bigint"+" COMMENT '继电器命令ID'"
    )
    private Long commonId;
    @Column(
            name = "create_date",
            nullable = false,
            columnDefinition="datetime"+" COMMENT '创建时间'"
    )
    private Date createDate;
    @Column(
            name = "update_date",
            nullable = false,
            columnDefinition="datetime"+" COMMENT '修改时间'"
    )
    private Date updateDate;
    @Column(
            name = "status",
            nullable = false,
            columnDefinition="bit"+" COMMENT '当前状态——true是新的状态，false是过期的状态'"
    )
    private Boolean status;

    public CommandStatus(String imei, Long commonId, Date createDate,Date updateDate, Boolean status) {
        this.imei = imei;
        this.commonId = commonId;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.status = status;
    }
}
