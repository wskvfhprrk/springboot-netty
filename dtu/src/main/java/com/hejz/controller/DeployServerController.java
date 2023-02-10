package com.hejz.controller;

import com.hejz.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    RestTemplate restTemplate;
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
    //定时远程访问此端口，如果查询不到就重启
    @GetMapping("heartbeat")//心跳
    public Result heartbeat(){
        return Result.ok();
    }
    //每分钟执行一次
    @Scheduled(cron = "0/59 * * * * ? ")
    public void sendheartbeat() throws IOException {
        String url = "http://nqql1sqmuqbt.ngrok.xiaomiqiu123.top/deployServer/heartbeat";
        try {
            ResponseEntity<Object> entity = restTemplate.getForEntity(url, null);
        }catch (Exception e){
                log.error("服务器心跳不见了，重启部署项目！");
                Runtime rt = Runtime.getRuntime();
                String[] cmd = {"/bin/sh","-c","shutdown -r now"};
                rt.exec(cmd);
        }
        //404 Not Found: "Tunnel nqql1sqmuqbt.ngrok.xiaomiqiu123.top not found,Please check whether the client is started!<EOL>"
    }

}
