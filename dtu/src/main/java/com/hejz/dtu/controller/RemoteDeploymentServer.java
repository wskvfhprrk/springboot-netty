package com.hejz.dtu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-02-18 06:42
 * @Description: 远程部署服务器
 */
@RestController
@Api(tags="远程部署服务器")
@Slf4j
public class RemoteDeploymentServer {
    @GetMapping("rmote")
    @ApiOperation("远程部署服务器")
    public void run() throws IOException {
        log.info("远程部署项目部署………………………………………………");
        Process process = Runtime.getRuntime().exec("sh /root/start.sh" );
        InputStreamReader ips = new InputStreamReader(process.getInputStream());
        BufferedReader br = new BufferedReader(ips);
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }
}
