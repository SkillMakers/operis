package com.operis.project.core.service.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

/**
 * Intercepteur HTTP pour gérer le Correlation ID dans le service Project.
 */
@Component
public class ProjectCorrelationIdInterceptor implements HandlerInterceptor {

    // Nom de l'en-tête pour le Correlation ID
    private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";

    /**
     * Avant que la requête ne soit traitée par le contrôleur.
     *
     * @param request  la requête HTTP entrante
     * @param response la réponse HTTP sortante
     * @param handler  le gestionnaire de la requête
     * @return true pour continuer le traitement
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Récupère le Correlation ID dans les en-têtes
        String correlationId = request.getHeader(CORRELATION_ID_HEADER);
        if (correlationId == null || correlationId.isEmpty()) {
            // Génère un nouveau Correlation ID si absent
            correlationId = UUID.randomUUID().toString();
        }

        // Ajouter le Correlation ID au MDC pour les logs
        MDC.put(CORRELATION_ID_HEADER, correlationId);

        // Ajoute le Correlation ID dans les en-têtes de la réponse
        response.setHeader(CORRELATION_ID_HEADER, correlationId);

        return true; // Continue le traitement
    }

    /**
     * Après que la requête ait été entièrement traitée.
     *
     * @param request  la requête HTTP entrante
     * @param response la réponse HTTP sortante
     * @param handler  le gestionnaire de la requête
     * @param ex       exception éventuelle levée lors du traitement
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // Nettoie le MDC après la requête
        MDC.clear();
    }
}
