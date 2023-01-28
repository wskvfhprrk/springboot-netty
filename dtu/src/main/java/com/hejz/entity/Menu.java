package com.hejz.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-28 20:31
 * @Description: 菜单
 */
@Data
@Entity(name = "tb_menu")
public class Menu {
    @Id
    //ID
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
    //路径
    @Column(
            name = "path",
            nullable = false,
            columnDefinition="varchar(100)"+" COMMENT '路径'"
    )
    private String path;
    //路由器的名字
    @Column(
            name = "name",
            nullable = false,
            columnDefinition="varchar(100)"+" COMMENT '路由器的名字'"
    )
    private String name;
    //标题
    @Column(
            name = "title",
            nullable = false,
            columnDefinition="varchar(100)"+" COMMENT '标题'"
    )
    private String title;
    //连接
    @Column(
            name = "url",
            nullable = false,
            columnDefinition="varchar(100)"+" COMMENT '连接'"
    )
    private String url;
    //图标
    @Column(
            name = "icon",
            nullable = false,
            columnDefinition="varchar(100)"+" COMMENT '图标'"
    )
    private String icon;
    //是否隐藏——true隐藏，false不隐藏（默认）
    @Column(
            name = "hidden",
            nullable = false,
            columnDefinition="bit"+" COMMENT '是否隐藏——true隐藏，false不隐藏（默认）'"
    )
    private Boolean hidden;
    //父级Id
    @Column(
            name = "parent_id",
            nullable = false,
            columnDefinition="int"+" COMMENT '父级Id'"
    )
    private Integer parentId;
    //类型——1父级菜单、2子菜单、3按纽
    @Column(
            name = "type",
            nullable = false,
            columnDefinition="int"+" COMMENT '类型——1父级菜单、2子菜单、3按纽'"
    )
    private Integer type;
    //排序编号
    @Column(
            name = "order_by_no",
            nullable = false,
            columnDefinition="int"+" COMMENT '排序编号'"
    )
    private Integer orderByNo;
    //多对多关系映射
    @ManyToMany
    @JoinTable(name="tb_role_menu",//中间表的名称
            //中间表tb_role_menu字段关联tb_role表的主键字段id
            joinColumns={@JoinColumn(name="tb_role",referencedColumnName="id")},
            //中间表tb_role_menu的字段关联tb_menu表的主键menu_id
            inverseJoinColumns={@JoinColumn(name="tb_menu",referencedColumnName="id")}
    )
    private Set<Role> roles=new HashSet<>();
}
