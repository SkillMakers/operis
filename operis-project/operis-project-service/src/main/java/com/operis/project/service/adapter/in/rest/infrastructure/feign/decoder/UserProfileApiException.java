package com.operis.project.service.adapter.in.rest.infrastructure.feign.decoder;

import lombok.Getter;

import static com.operis.project.service.adapter.out.http.UserProfileFeignClient.UserProfileApiError;

@Getter
public class UserProfileApiException extends RuntimeException {

    private final UserProfileApiError apiError;

    public UserProfileApiException(String message, UserProfileApiError apiError) {
        super(message);
        this.apiError = apiError;
    }
}
