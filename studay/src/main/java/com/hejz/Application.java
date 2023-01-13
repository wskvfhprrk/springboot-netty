package com.hejz;

import com.hejz.nettyserver.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * author: hejz
 * data:  2022-5-9
 */
@SpringBootApplication
public class Application implements CommandLineRunner {
    @Autowired
    private NettyServer nettyServer;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        nettyServer.serverRun();
    }
}
