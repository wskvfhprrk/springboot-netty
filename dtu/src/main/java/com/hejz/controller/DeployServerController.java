package com.hejz.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags ="部署服务器")
public class DeployServerController {
    @GetMapping
    @ApiOperation("部署服务器")
    public void deployServer(){
        try {
            Runtime rt = Runtime.getRuntime();
            String[] cmd = {"/bin/sh","-c","shutdown -r now"};
            rt.exec(cmd);

        } catch (IOException  e) {
            log.error("代码重新部署服务器失败",e.fillInStackTrace());
        }
    }
}
