package com.operis.user.profile.core.service.config;

import com.operis.user.profile.core.service.interceptor.UserProfileCorrelationIdInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private UserProfileCorrelationIdInterceptor userProfileCorrelationIdInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userProfileCorrelationIdInterceptor);
    }
}
