package com.operis.project.core.service.adapter.in.rest.model;

import java.util.List;

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
        this(status, message, List.of());
    }
}
