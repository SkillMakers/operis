package com.operis.subscription.code.model.adapter.in;

import com.operis.subscription.code.model.UserSubscription;
import com.operis.subscription.code.model.port.in.UserSubscriptionUseCases;
import com.operis.subscription.code.model.port.out.persistence.UserSubscriptionRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserSubscriptionService implements UserSubscriptionUseCases {

    private final UserSubscriptionRepository userSubscriptionRepository;

    @Override
    public UserSubscription subscribeUser(UserSubscription userSubscription) {
        return userSubscriptionRepository.save(userSubscription);
    }

    @Override
    public void unsubscribe(Long userSubscriptionId) {
        userSubscriptionRepository.delete(userSubscriptionId);
    }
}
