package com.operis.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "operis-task-service")
public interface TaskClient {

}
