package com.operis.filter;

import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;


/**
 * Filtre global du Gateway pour gérer et propager le Correlation ID dans les requêtes et les logs.
 */
@Component
public class GatewayCorrelationIdFilter implements GlobalFilter, Ordered {

    private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";

    /**
     * Méthode principale du filtre, exécutée pour chaque requête.
     *
     * @param exchange l'échange actuel contenant la requête et la réponse
     * @param chain    la chaîne de filtres à exécuter
     * @return un Mono<Void> indiquant l'état de traitement
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // Récupère la requête entrante
        ServerHttpRequest request = exchange.getRequest();

        // Cherche un Correlation ID dans les en-têtes de la requête
        String correlationId = request.getHeaders().getFirst(CORRELATION_ID_HEADER);
        if (correlationId == null || correlationId.isEmpty()) {
            // Génère un Correlation ID si aucun n'est présent
            correlationId = UUID.randomUUID().toString();
        }

        // Ajoute le Correlation ID au MDC (Mapped Diagnostic Context) pour les logs
        MDC.put(CORRELATION_ID_HEADER, correlationId);

        // Ajouter ou propager le Correlation ID dans les en-têtes des requêtes vers les services en aval
        ServerHttpRequest mutatedRequest = request.mutate()
                .header(CORRELATION_ID_HEADER, correlationId)
                .build();

        // Créer un échange modifié avec la nouvelle requête
        ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();

        // Continue le traitement de la requête via la chaîne de filtres
        return chain.filter(mutatedExchange).doFinally(signalType -> {
            // Nettoie le MDC après le traitement de la requête
            MDC.clear();
        });
    }

    /**
     * Définit l'ordre d'exécution du filtre.
     *
     * @return la priorité du filtre
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE; // Exécuter ce filtre en priorité
    }
}
