package com.operis.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "operis-project-service")
public interface TaskClient {

}
