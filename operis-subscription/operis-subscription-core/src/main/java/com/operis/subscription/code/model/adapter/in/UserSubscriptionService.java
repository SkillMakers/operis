package com.operis.subscription.code.model.adapter.in;

import com.operis.subscription.code.model.Subscription;
import com.operis.subscription.code.model.UserSubscription;
import com.operis.subscription.code.model.model.GetUserSubscriptionsCommand;
import com.operis.subscription.code.model.model.SubscribeUserCommand;
import com.operis.subscription.code.model.port.in.UserSubscriptionUseCases;
import com.operis.subscription.code.model.port.out.persistence.SubscriptionRepository;
import com.operis.subscription.code.model.port.out.persistence.UserSubscriptionRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserSubscriptionService implements UserSubscriptionUseCases {

    private final UserSubscriptionRepository userSubscriptionRepository;

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public void subscribeUser(SubscribeUserCommand subscribeUserCommand) {
        Subscription subscription = subscriptionRepository.findById(subscribeUserCommand.subscriptionId());
        userSubscriptionRepository.save(new UserSubscription(subscribeUserCommand.userEMail(), subscription));
    }

    @Override
    public void unsubscribe(String userEmail) {
        userSubscriptionRepository.delete(userEmail);
    }

    @Override
    public UserSubscription get(GetUserSubscriptionsCommand command) {
        return userSubscriptionRepository.find(command.userEmail()).orElse(null);
    }
}
