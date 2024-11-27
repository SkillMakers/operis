package com.operis.subscription.code.model.adapter.in;

import com.operis.subscription.code.model.Subscription;
import com.operis.subscription.code.model.UserSubscription;
import com.operis.subscription.code.model.model.GetUserSubscriptionsCommand;
import com.operis.subscription.code.model.model.SubscribeUserCommand;
import com.operis.subscription.code.model.port.in.UserSubscriptionUseCases;
import com.operis.subscription.code.model.port.out.persistence.SubscriptionRepository;
import com.operis.subscription.code.model.port.out.persistence.UserSubscriptionRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class UserSubscriptionService implements UserSubscriptionUseCases {

    private final UserSubscriptionRepository userSubscriptionRepository;

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public void subscribeUser(SubscribeUserCommand subscribeUserCommand) {
        Subscription subscription = subscriptionRepository.findById(subscribeUserCommand.subscriptionId());
        userSubscriptionRepository.save(new UserSubscription(UUID.randomUUID().toString(), subscribeUserCommand.userEMail(), subscription));
    }

    @Override
    public void unsubscribe(String userSubscriptionId) {
        userSubscriptionRepository.delete(userSubscriptionId);
    }

    @Override
    public UserSubscription get(GetUserSubscriptionsCommand command) {
        return userSubscriptionRepository.find(command.userEmail()).orElse(null);
    }
}
