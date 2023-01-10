package com.hejz.studay.entity;

import lombok.Data;
import java.io.Serializable;
import javax.persistence.*;

/**
 * 用户信息实体类
 * author: hejz
 * data: 2022-5-9
 */
@Data
@Entity(name = "tb_user")
public class User implements Serializable{

    @Id
    @Column(
            name = "id",
            nullable = false,
            columnDefinition="int"+" COMMENT ''"
    )
    private Integer id;

    @Column(
            name = "username",
            nullable = false,
            columnDefinition="varchar(255)"+" COMMENT '用户名'"
    )
    private String username;

    @Column(
            name = "age",
            nullable = true,
            columnDefinition="int"+" COMMENT '年龄'"
    )
    private Integer age;

    @Column(
            name = "role_id",
            nullable = true,
            columnDefinition="int"+" COMMENT '角色id'"
    )
    private Integer roleId;
}
