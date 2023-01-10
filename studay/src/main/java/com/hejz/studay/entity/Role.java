package com.hejz.studay.entity;

import lombok.Data;
import java.io.Serializable;
import javax.persistence.*;

/**
 * 角色实体类
 * author: hejz
 * data: 2022-5-9
 */
@Data
@Entity(name = "tb_role")
public class Role implements Serializable{

    @Id
    @SequenceGenerator(
            name = "tb_role_sequence",
            sequenceName = "tb_role_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tb_role_sequence"
    )
    @Column(
            name = "id",
            nullable = false,
            columnDefinition="int"+" COMMENT 'id'"
    )
    private Integer id;

    @Column(
            name = "name",
            nullable = true,
            columnDefinition="varchar(255)"+" COMMENT '名称'"
    )
    private String name;
}
