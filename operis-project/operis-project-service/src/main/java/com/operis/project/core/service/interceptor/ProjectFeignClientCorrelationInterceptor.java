package com.operis.project.core.service.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;


/**
 * Intercepteur Feign pour propager le Correlation ID dans les requêtes sortantes.
 */
@Component
public class ProjectFeignClientCorrelationInterceptor implements RequestInterceptor {

    // Nom de l'en-tête pour le Correlation ID
    private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";

    /**
     * Intercepte et modifie les requêtes Feign avant qu'elles ne soient envoyées.
     *
     * @param template le modèle de requête Feign
     */
    @Override
    public void apply(RequestTemplate template) {
        // Récupérer le Correlation ID du MDC
        String correlationId = MDC.get(CORRELATION_ID_HEADER);
        // Ajoute le Correlation ID dans les en-têtes de la requête Feign
        if (correlationId != null) {
            template.header(CORRELATION_ID_HEADER, correlationId);
        }
    }
}
