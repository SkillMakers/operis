package com.operis.project.core.service.adapter.in.rest.handler;

import com.operis.project.core.application.project.model.exception.IllegalProjectMemberException;
import com.operis.project.core.application.project.model.exception.ProjectNotFoundException;
import com.operis.project.core.service.adapter.in.rest.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProjectExceptionHandler {
    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ApiError> handleProjectNotFoundException(ProjectNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler(IllegalProjectMemberException.class)
    public ResponseEntity<ApiError> handleIllegalProjectMemberException(IllegalProjectMemberException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiError(HttpStatus.CONFLICT.value(), ex.getMessage()));
    }
}
