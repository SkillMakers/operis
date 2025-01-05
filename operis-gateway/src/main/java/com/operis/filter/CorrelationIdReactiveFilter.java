package com.operis.filter;

import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class CorrelationIdReactiveFilter implements GlobalFilter, Ordered {

    private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // Récupérer ou générer un Correlation ID
        String correlationId = request.getHeaders().getFirst(CORRELATION_ID_HEADER);
        if (correlationId == null || correlationId.isEmpty()) {
            correlationId = UUID.randomUUID().toString();
        }

        // Ajouter le Correlation ID au MDC pour les logs
        MDC.put(CORRELATION_ID_HEADER, correlationId);

        // Ajouter ou propager le Correlation ID dans les en-têtes des requêtes vers les services en aval
        ServerHttpRequest mutatedRequest = request.mutate()
                .header(CORRELATION_ID_HEADER, correlationId)
                .build();

        // Créer un échange modifié avec la nouvelle requête
        ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();

        // Continuer la chaîne de filtres
        return chain.filter(mutatedExchange).doFinally(signalType -> {
            // Nettoyer le MDC après la requête
            MDC.clear();
        });
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE; // S'assurer que ce filtre est exécuté en premier
    }
}
