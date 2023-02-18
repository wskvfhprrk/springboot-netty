package com.hejz.dtu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-02-18 06:42
 * @Description: 远程部署服务器
 */
@RestController
@Api(tags="远程部署服务器")
@Slf4j
public class RemoteDeploymentServer {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("remote")
    @ApiOperation("远程部署服务器")
    public void run() throws Exception {
        log.info("项目部署………………………………………………");
        restTemplate.getForEntity("http://www.localhost:8090",null);
    }
}
