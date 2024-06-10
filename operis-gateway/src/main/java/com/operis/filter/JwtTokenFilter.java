package com.operis.filter;

import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
public class JwtTokenFilter implements WebFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private final ServerSecurityContextRepository securityContextRepository = new WebSessionServerSecurityContextRepository();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String header = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        String path = exchange.getRequest().getPath().toString();
        if (path.equals("/api/auth/login") || path.equals("/api/users/create")) {
           // passer à la chaîne suivante sans vérification
            return chain.filter(exchange);
        }

        if (header == null || !header.startsWith("Bearer ")) {
            return this.unauthorizedResponse(exchange.getResponse());
        }

        String token = header.substring(7);
        JWTClaimsSet jwtClaimsSet ;
        try {
            jwtClaimsSet = jwtTokenUtil.parseToken(token);
        } catch (Exception e) {
            return this.unauthorizedResponse(exchange.getResponse());
        }

        String username = jwtClaimsSet.getSubject();
        if (username != null) {
            UserDetails userDetails = new User(username, "", Collections.emptyList());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextImpl securityContext = new SecurityContextImpl();
            securityContext.setAuthentication(authentication);

            return this.securityContextRepository.save(exchange, securityContext)
                    .then(chain.filter(exchange))
                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
        }

        return chain.filter(exchange);
    }

    private Mono<Void> unauthorizedResponse(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

}
