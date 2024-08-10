package com.operis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OperisUserAccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OperisUserAccountServiceApplication.class, args);
    }

}
