package com.operis.user.profile.core.service.config;

import com.operis.user.profile.core.application.port.outbound.UserProfilePersistencePort;
import com.operis.user.profile.core.domain.service.UserProfileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserProfileAppConfig {

    @Autowired
    private UserProfilePersistencePort jpaUserProfileRepository;

    @Bean
    public UserProfileServiceImpl userProfileService() {
        return new UserProfileServiceImpl(jpaUserProfileRepository);
    }
}
