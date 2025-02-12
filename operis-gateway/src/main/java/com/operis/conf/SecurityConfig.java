package com.operis.conf;

import com.operis.filter.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/api/auth/login", "/api/users/create", "/project-service/**", "/actuator/**").permitAll()
                        .pathMatchers("/api/projects/**").hasAnyRole("FAKE_ADMIN_FOR_DEMO") // Everyone has this fake role, just for demo
                        .anyExchange().authenticated()
                )
                // Ajoutez ici votre JwtTokenFilter avant le filtre de sécurité principal
                .addFilterBefore(jwtTokenFilter, SecurityWebFiltersOrder.AUTHENTICATION) // Cette ligne place votre filtre avant l'authentification
                .build();
    }
}
