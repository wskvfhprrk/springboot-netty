package com.hejz.dtu.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * 实体类
 * author: hejz菜单
 * data: 2023-2-7
 */
@Data
@Entity(name = "tb_menu")
@org.hibernate.annotations.Table(appliesTo = "tb_menu", comment = "菜单")
public class Menu implements Serializable{

    @Id
    @SequenceGenerator(
            name = "tb_menu_sequence",
            sequenceName = "tb_menu_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tb_menu_sequence"
    )
    @Column(
            name = "id",
            nullable = false,
            columnDefinition="int"+" COMMENT '菜单ID'"
    )
    private Integer id;

    @Column(
            name = "hidden",
            nullable = false,
            columnDefinition="date"+" COMMENT '是否隐藏——true隐藏，false不隐藏（默认）'"
    )
    private Boolean hidden;

    @Column(
            name = "icon",
            nullable = false,
            columnDefinition="varchar(100)"+" COMMENT '图标'"
    )
    private String icon;

    @Column(
            name = "name",
            nullable = false,
            columnDefinition="varchar(100)"+" COMMENT '路由器的名字'"
    )
    private String name;

    @Column(
            name = "order_by_no",
            nullable = false,
            columnDefinition="int"+" COMMENT '排序编号'"
    )
    private Integer orderByNo;

    @Column(
            name = "parent_id",
            nullable = false,
            columnDefinition="int"+" COMMENT '父级Id'"
    )
    private Integer parentId;

    @Column(
            name = "path",
            nullable = false,
            columnDefinition="varchar(100)"+" COMMENT '路径'"
    )
    private String path;

    @Column(
            name = "title",
            nullable = false,
            columnDefinition="varchar(100)"+" COMMENT '标题'"
    )
    private String title;

    @Column(
            name = "type",
            nullable = false,
            columnDefinition = "int" + " COMMENT '类型——1父级菜单、2子菜单、3按纽'"
    )
    private Integer type;

    @Column(
            name = "url",
            nullable = false,
            columnDefinition = "varchar(100)" + " COMMENT '连接'"
    )
    private String url;

    @ManyToMany(mappedBy = "menus")
    private Set<Role> roles;
}
