package com.operis.user.profile.core.service.config;

import com.operis.user.profile.core.application.port.in.UserProfileUseCases;
import com.operis.user.profile.core.application.port.out.persistence.UserProfileRepository;
import com.operis.user.profile.core.application.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserProfileAppConfig {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Bean
    public UserProfileUseCases userProfileService() {
        return new UserProfileService(userProfileRepository);
    }
}
