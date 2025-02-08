package com.operis.project.core.service.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;


/**
 * Intercepteur Feign pour propager le Correlation ID dans les requêtes sortantes.
 */
@Component
public class FeignClientCorrelationIdInterceptor implements RequestInterceptor {

    private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";

    /**
     * Intercepte et modifie les requêtes Feign avant qu'elles ne soient envoyées.
     */
    @Override
    public void apply(RequestTemplate template) {
        String correlationId = MDC.get(CORRELATION_ID_HEADER);

        if (correlationId != null) {
            // Ajoute le Correlation ID dans les en-têtes de la requête Feign
            template.header(CORRELATION_ID_HEADER, correlationId);
        }
    }
}
