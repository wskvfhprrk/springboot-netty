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

import java.io.*;

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
    @ApiOperation("服务器心跳请求")
    public Result heartbeat(){
        return Result.ok();
    }
    //每分钟执行一次
//    @Scheduled(cron = "0/59 * * * * ? ")
    public void sendheartbeat() throws IOException {
        String url = "http://nqql1sqmuqbt.ngrok.xiaomiqiu123.top/deployServer/heartbeat";
        try {
            ResponseEntity<Object> entity = restTemplate.getForEntity(url, null);
        } catch (Exception e) {
            //404 Not Found: "Tunnel nqql1sqmuqbt.ngrok.xiaomiqiu123.top not found,Please check whether the client is started!<EOL>"
            log.error("服务器心跳不见了，重启部署项目！");
//            Runtime rt = Runtime.getRuntime();
////            String[] cmd = {"/bin/sh","-c","nohup ./xiaomiqiu -authtoken=bAe854993e6444e3925b24c7edcdd72A -log=xiaomiqiu.log -log-level=info start-all & > /dev/null 2>&1 &"};
//            String[] cmd = {"/bin/sh", "-c", "shutdown -r now"};
//            rt.exec(cmd);
//            run();
        }
    }

//    public void run(){
//        try {
//            String cmd = "start1.sh";
//            Runtime runtime = Runtime.getRuntime();
//            Process exec= runtime.exec("/bin/sh","-c","start1.sh);
//            if (FileUtil.isWindows()) {
//                exec = runtime.exec("cmd /c cd " + file + " && " + cmd + ".bat");
//            } else {
//                exec = runtime.exec("bash " + cmd + ".sh", null, new File(file));
//            }
//            exec.waitFor();
//            //取得命令结果的输出流
//            InputStream is = exec.getInputStream();
//            //用一个读输出流类去读
//            InputStreamReader isr = new InputStreamReader(is);
//            BufferedReader br = new BufferedReader(isr);
//
//            String line;
//            while ((line = br.readLine()) != null) {
//                System.out.println(line);
//            }
//            is.close();
//            isr.close();
//            br.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
