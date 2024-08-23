package com.operis.subscription.code.model.adapter.in;

import com.operis.subscription.code.model.Subscription;
import com.operis.subscription.code.model.port.in.SubscriptionUseCases;
import com.operis.subscription.code.model.port.out.persistence.SubscriptionRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SubscriptionService implements SubscriptionUseCases {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public Subscription addSubscription(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }
}
