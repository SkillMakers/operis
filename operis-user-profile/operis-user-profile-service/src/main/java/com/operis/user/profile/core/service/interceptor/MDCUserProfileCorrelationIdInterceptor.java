package com.operis.user.profile.core.service.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;


/**
 * Intercepteur HTTP pour gérer le Correlation ID dans le service User Profile.
 */
@Component
public class MDCUserProfileCorrelationIdInterceptor implements HandlerInterceptor {

    private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";

    /**
     * Avant que la requête ne soit traitée par le contrôleur.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String correlationId = request.getHeader(CORRELATION_ID_HEADER);
        if (correlationId == null || correlationId.isEmpty()) {
            correlationId = UUID.randomUUID().toString();
        }

        // Ajoute le Correlation ID au MDC (Mapped Diagnostic Context) pour les logs
        MDC.put(CORRELATION_ID_HEADER, correlationId);

        return true;
    }

    /**
     * Après que la requête ait été entièrement traitée.
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        MDC.clear();
    }
}
