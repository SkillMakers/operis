package com.operis.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class CorrelationIdFilter implements Filter {

    private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Récupérer le Correlation ID ou en générer un nouveau
        String correlationId = httpRequest.getHeader(CORRELATION_ID_HEADER);
        if (correlationId == null || correlationId.isEmpty()) {
            correlationId = UUID.randomUUID().toString();
        }

        // Ajouter le Correlation ID au MDC pour les logs
        MDC.put(CORRELATION_ID_HEADER, correlationId);

        // Ajouter le Correlation ID aux en-têtes de réponse
        httpResponse.setHeader(CORRELATION_ID_HEADER, correlationId);

        try {
            // Continuer la chaîne de filtres
            chain.doFilter(request, response);
        } finally {
            // Nettoyer le MDC après la requête
            MDC.clear();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
