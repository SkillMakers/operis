package com.operis.project.core.service.adapter.in.rest.handler;

import com.operis.project.core.application.project.model.exception.IllegalProjectMemberException;
import com.operis.project.core.application.project.model.exception.ProjectNotFoundException;
import com.operis.project.core.service.adapter.in.rest.model.ProjectApiError;
import com.operis.project.core.service.adapter.out.http.model.UserProfileApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ProjectExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<Object> handleProjectNotFoundException(ProjectNotFoundException ex, WebRequest request) {
        var body = new ProjectApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), ex.getMessage());
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(IllegalProjectMemberException.class)
    public ResponseEntity<Object> handleIllegalProjectMemberException(IllegalProjectMemberException ex, WebRequest request) {
        var body = new ProjectApiError(HttpStatus.CONFLICT.value(), ex.getMessage(), ex.getMessage());
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> fieldErrors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.add(error.getDefaultMessage());
        }
        ProjectApiError body = new ProjectApiError(status.value(), HttpStatus.valueOf(status.value()).name(), "Validation failed for one or more fields.",
                fieldErrors);

        return handleExceptionInternal(ex, body, headers, status, request);
    }

    @ExceptionHandler(UserProfileApiException.class)
    public ResponseEntity<Object> handleUserProfileApiException(UserProfileApiException ex, WebRequest request) {
        var body = new ProjectApiError(ex.getApiError().statusCode(), ex.getApiError().httpStatus(), ex.getApiError().message());
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatusCode.valueOf(ex.getApiError().statusCode()), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        log.error("An error occurred while processing the request.", ex);

        if (!(body instanceof ProjectApiError)) {
            body = new ProjectApiError(statusCode.value(), HttpStatus.valueOf(statusCode.value()).name(), ex.getMessage());
        }

        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }
}

