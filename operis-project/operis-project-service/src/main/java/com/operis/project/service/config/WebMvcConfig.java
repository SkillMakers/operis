package com.operis.project.service.config;

import com.operis.project.service.adapter.in.rest.infrastructure.interceptor.MDCProjectCorrelationIdInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Classe de configuration pour le service Project.
 * <p>
 * Permet d'ajouter des intercepteurs à la chaîne de traitement des requêtes HTTP.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private MDCProjectCorrelationIdInterceptor correlationIdInterceptor;

    /**
     * Méthode pour enregistrer les intercepteurs dans le registre.
     *
     * @param registry le registre des intercepteurs
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(correlationIdInterceptor);
    }
}
