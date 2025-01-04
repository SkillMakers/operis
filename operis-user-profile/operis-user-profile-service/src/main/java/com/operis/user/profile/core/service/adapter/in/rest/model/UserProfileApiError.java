package com.operis.user.profile.core.service.adapter.in.rest.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserProfileApiError(Integer statusCode,
                                  String httpStatus,
                                  String message,
                                  List<String> details) {

    public UserProfileApiError {
        if (statusCode == null) {
            throw new IllegalArgumentException("Status must not be null");
        }
        if (httpStatus == null) {
            throw new IllegalArgumentException("Http Status must not be null");
        }

        if (message == null) {
            throw new IllegalArgumentException("Message must not be null");
        }
    }

    public UserProfileApiError(Integer statusCode, String httStatus, String message) {
        this(statusCode, httStatus, message, null);
    }
}
