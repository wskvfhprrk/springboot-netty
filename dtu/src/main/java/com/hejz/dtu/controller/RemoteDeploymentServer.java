package com.hejz.dtu.controller;

import com.hejz.dtu.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-02-18 06:42
 * @Description: 远程部署服务器
 */
@RestController
@Api(tags="远程部署服务器")
@Slf4j
public class RemoteDeploymentServer {

    @GetMapping("remote")
    @ApiOperation("远程部署服务器")
    public void run() throws Exception {
        log.info("项目部署………………………………………………");
        String command = "shutdown -r now";
        String[] cmdStrings = new String[]{"sh", "-c", command};

        Process p;
        p = Runtime.getRuntime().exec(cmdStrings);
        int status = p.waitFor();
        if (status != 0) {
            System.err.println(String.format("runShellCommand: %s, status: %s", command,
                    status));
        }
    }

    @GetMapping("/deployServer/heartbeat")
    @ApiOperation("心跳")
    public Result heartbeat(){
        return Result.ok();
    }
}
