package com.operis.project.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.operis.project.service.adapter.in.rest.infrastructure.feign.decoder.GlobalErrorDecoder;
import com.operis.project.service.adapter.in.rest.infrastructure.logging.CustomFeignLogger;
import feign.Logger;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenFeignConfig {

    @Bean
    public ErrorDecoder globalErrorDecoder(ObjectMapper objectMapper) {
        return new GlobalErrorDecoder(objectMapper);
    }

    @Bean
    public Logger feignLogger() {
        return new CustomFeignLogger();
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
