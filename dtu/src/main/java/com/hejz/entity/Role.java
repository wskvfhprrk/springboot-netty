package com.hejz.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
            columnDefinition="int"+" COMMENT 'ID'"
    )
    private Integer id;

    @Column(
            name = "name",
            nullable = true,
            columnDefinition="varchar(255)"+" COMMENT '名称'"
    )
    private String name;

    //多对多关系映射
    @ManyToMany
    @JoinTable(name="tb_user_role",//中间表的名称
            //中间表user_role_rel字段关联sys_role表的主键字段role_id
            joinColumns={@JoinColumn(name="role_id",referencedColumnName="id")},
            //中间表user_role_rel的字段关联sys_user表的主键user_id
            inverseJoinColumns={@JoinColumn(name="user_id",referencedColumnName="id")}
    )
    private Set<User> users = new HashSet<User>(0);

    //多对多关系映射
    @ManyToMany
    @JoinTable(name = "tb_role_menu",//中间表的名称
            //中间表tb_role_menu字段关联tb_role表的主键字段id
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            //中间表tb_role_menu的字段关联tb_menu表的主键menu_id
            inverseJoinColumns = {@JoinColumn(name = "menu_id", referencedColumnName = "id")}
    )
    private Set<Menu> menus = new HashSet<>();
}
