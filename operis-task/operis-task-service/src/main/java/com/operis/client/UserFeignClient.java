package com.operis.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = " operis-user-service")
public interface UserFeignClient {

}
