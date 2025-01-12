package com.operis.project.core.service.config;

import com.operis.project.core.service.interceptor.ProjectCorrelationIdInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Classe de configuration pour le service Project.
 * Permet d'ajouter des intercepteurs à la chaîne de traitement des requêtes HTTP.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private ProjectCorrelationIdInterceptor projectCorrelationIdInterceptor;

    /**
     * Méthode pour enregistrer les intercepteurs dans le registre.
     *
     * @param registry le registre des intercepteurs
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Ajout de l'intercepteur ProjectCorrelationIdInterceptor au registre
        // Cet intercepteur sera appliqué à toutes les requêtes HTTP entrantes
        registry.addInterceptor(projectCorrelationIdInterceptor);
    }
}
