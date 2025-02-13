package com.operis.project.service.adapter.in.rest.infrastructure.logging;

import feign.Logger;
import feign.Request;
import feign.Response;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class CustomFeignLogger extends Logger {

    @Override
    protected void log(String configKey, String format, Object... args) {
        log.info(String.format(methodTag(configKey) + format, args));
    }

    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
        String requestBody = request.body() != null ? new String(request.body(), StandardCharsets.UTF_8) : "No Body";
        log(configKey, "Feign Request - Method: %s | URL: %s | Body: %s", request.httpMethod(), request.url(), requestBody);
    }

    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
        String responseBody = "No Body";
        if (response.body() != null) {
            byte[] bodyData = response.body().asInputStream().readAllBytes();
            responseBody = new String(bodyData, StandardCharsets.UTF_8);
            response = response.toBuilder().body(bodyData).build(); // Rebuffer la réponse pour éviter qu'elle ne soit consommée
        }
        log(configKey, "Feign Response - Status: %d | Body: %s", response.status(), responseBody);
        return response;
    }
}
