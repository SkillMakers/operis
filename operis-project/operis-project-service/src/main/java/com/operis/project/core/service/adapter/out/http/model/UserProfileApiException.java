package com.operis.project.core.service.adapter.out.http.model;

import lombok.Getter;

import static com.operis.project.core.service.adapter.out.http.UserProfileFeignClient.UserProfileApiError;

@Getter
public class UserProfileApiException extends RuntimeException {

    private final UserProfileApiError apiError;

    public UserProfileApiException(String message, UserProfileApiError apiError) {
        super(message);
        this.apiError = apiError;
    }
}
