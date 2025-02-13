package com.operis.project.service.adapter.in.rest.infrastructure.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class ProjectLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        log.info("Request URI: {} | Method: {}", request.getRequestURI(), request.getMethod());

        filterChain.doFilter(request, response);

        log.info("Response Status: {}", response.getStatus());
    }
}
