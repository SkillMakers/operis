package com.operis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OperisGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(OperisGatewayApplication.class, args);
    }

}
