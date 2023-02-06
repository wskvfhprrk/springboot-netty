package com.hejz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户信息实体类
 * author: hejz
 * data: 2022-5-9
 */
@Data
@Entity(name = "tb_user")
@org.hibernate.annotations.Table(appliesTo = "tb_user", comment = "用户信息")
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
            columnDefinition = "int" + " COMMENT '年龄'"
    )
    private Integer age;

    //多对多关系映射
    @ManyToMany(mappedBy = "users")
    private Set<Role> roles = new HashSet<>(0);

    public User(Integer id) {
        this.id = id;
    }
}
