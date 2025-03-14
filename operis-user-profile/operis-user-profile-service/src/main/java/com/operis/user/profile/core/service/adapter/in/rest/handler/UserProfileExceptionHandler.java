package com.operis.user.profile.core.service.adapter.in.rest.handler;

import com.operis.user.profile.core.service.adapter.in.rest.model.UserProfileApiError;
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
public class UserProfileExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> fieldErrors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.add(error.getDefaultMessage());
        }
        UserProfileApiError body = new UserProfileApiError(status.value(), HttpStatus.valueOf(status.value()).name(), "Validation failed for one or more fields.",
                fieldErrors);

        return handleExceptionInternal(ex, body, headers, status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        log.error("An error occurred while processing the request.", ex);

        if (!(body instanceof UserProfileApiError)) {
            body = new UserProfileApiError(statusCode.value(), HttpStatus.valueOf(statusCode.value()).name(), ex.getMessage());
        }

        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }
}

