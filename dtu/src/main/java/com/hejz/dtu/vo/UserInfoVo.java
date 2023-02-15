package com.hejz.dtu.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-28 06:47
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVo {
    /**
     * roles : ["admin"]
     * introduction : I am a super administrator
     * avatar : https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif
     * name : Super Admin
     */
    private String introduction;
    private String avatar;
    private String name;
    private List<String> roles;

}