package com.hejz.dtu.vo;

import com.hejz.dtu.entity.Role;
import lombok.Data;

import java.util.Set;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-02-16 19:55
 * @Description:
 */
@Data
public class UserFindAllVo {
    private Integer id;
    private Integer age;
    private String username;
    private Set<Role> roles;
}
