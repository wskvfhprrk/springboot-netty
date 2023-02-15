package com.hejz.dtu.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * 用户信息实体类
 * author: hejz
 * data: 2023-2-7
 */
@Data
@Entity(name = "sys_user")
@org.hibernate.annotations.Table(appliesTo = "sys_user", comment = "用户信息")
public class User implements Serializable{

    @Id
    @SequenceGenerator(
            name = "sys_user_sequence",
            sequenceName = "sys_user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sys_user_sequence"
    )
    @Column(
            name = "id",
            nullable = false,
            columnDefinition = "int" + " COMMENT '用户信息ID'"
    )
    private Integer id;

    @Column(
            name = "age",
            nullable = true,
            columnDefinition = "int" + " COMMENT '年龄'"
    )
    private Integer age;

    @Column(
            name = "username",
            nullable = false,
            columnDefinition = "varchar(255)" + " COMMENT '用户名'"
    )
    private String username;

    @JsonIgnoreProperties(value = {"users"})
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "mid_user_role",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<Role> roles;
}
