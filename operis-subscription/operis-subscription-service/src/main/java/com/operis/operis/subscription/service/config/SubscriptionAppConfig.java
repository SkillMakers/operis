package com.operis.operis.subscription.service.config;

import com.operis.subscription.code.model.adapter.in.SubscriptionService;
import com.operis.subscription.code.model.adapter.in.UserSubscriptionService;
import com.operis.subscription.code.model.port.in.SubscriptionUseCases;
import com.operis.subscription.code.model.port.in.UserSubscriptionUseCases;
import com.operis.subscription.code.model.port.out.persistence.SubscriptionRepository;
import com.operis.subscription.code.model.port.out.persistence.UserSubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SubscriptionAppConfig {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserSubscriptionRepository userSubscriptionRepository;

    @Bean
    public SubscriptionUseCases subscriptionService() {
        return new SubscriptionService(subscriptionRepository);
    }

    @Bean
    public UserSubscriptionUseCases userSubscriptionService() {
        return new UserSubscriptionService(userSubscriptionRepository, subscriptionRepository);
    }
}
