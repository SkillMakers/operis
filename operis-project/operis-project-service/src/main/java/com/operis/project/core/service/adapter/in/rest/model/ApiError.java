package com.operis.project.core.service.adapter.in.rest.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(Integer status, String message, List<String> details) {

    public ApiError {
        if (status == null) {
            throw new IllegalArgumentException("Status must not be null");
        }

        if (message == null) {
            throw new IllegalArgumentException("Message must not be null");
        }
    }

    public ApiError(Integer status, String message) {
        this(status, message, null);
    }
}
