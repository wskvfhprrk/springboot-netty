package com.hejz.dtu.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;

/**
 * 角色实体类实体类
 * author: hejz
 * data: 2023-2-7
 */
@Data
@Entity(name = "tb_role")
@org.hibernate.annotations.Table(appliesTo = "tb_role", comment = "角色实体类")
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
            columnDefinition="int"+" COMMENT '角色ID'"
    )
    private Integer id;

    @Column(
            name = "name",
            nullable = true,
            columnDefinition="varchar(255)"+" COMMENT '名称'"
    )
    private String name;
    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    private Set<User> users;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_menu",
    joinColumns = @JoinColumn(name = "menu_id",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
    private Set<Menu> menus;
}
