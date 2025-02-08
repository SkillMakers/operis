package com.operis.handler;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserAccountApiError(Integer statusCode,
                                  String httpStatus,
                                  String message,
                                  List<String> details) {

    public UserAccountApiError {
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

    public UserAccountApiError(Integer statusCode, String httStatus, String message) {
        this(statusCode, httStatus, message, null);
    }
}
