package com.operis.project.service.adapter.in.rest.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProjectApiError(Integer statusCode,
                              String httpStatus,
                              String message,
                              List<String> details) {

    public ProjectApiError {
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

    public ProjectApiError(Integer statusCode, String httStatus, String message) {
        this(statusCode, httStatus, message, null);
    }
}
