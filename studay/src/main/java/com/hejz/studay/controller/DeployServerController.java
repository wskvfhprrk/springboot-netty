package com.hejz.studay.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-12 18:48
 * @Description: 部署服务器
 */
@RestController
@RequestMapping("deployServer")
@Slf4j
public class DeployServerController {
    @GetMapping
    public String deployServer(){
        //p = Runtime.getRuntime().exec(SHELL_FILE_DIR + RUNNING_SHELL_FILE + " "+param1+" "+param2+" "+param3);
        //p.waitFor();
        try {
            Runtime.getRuntime().exec("/root/start.sh").waitFor();
            log.info("代码重新部署服务器成功……");
            return "代码重新部署服务器成功！";
        } catch (IOException | InterruptedException e) {
            log.error("代码重新部署服务器失败",e.fillInStackTrace());
//            e.printStackTrace();
        }
        return "代码重新部署服务器失败！";
    }
}
