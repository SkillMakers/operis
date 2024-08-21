package com.operis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OperisTaskServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OperisTaskServiceApplication.class, args);
    }

}
