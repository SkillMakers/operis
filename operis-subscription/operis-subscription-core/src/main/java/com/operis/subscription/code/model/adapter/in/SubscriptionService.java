package com.operis.subscription.code.model.adapter.in;

import com.operis.subscription.code.model.Subscription;
import com.operis.subscription.code.model.model.CreateSubscriptionCommand;
import com.operis.subscription.code.model.port.in.SubscriptionUseCases;
import com.operis.subscription.code.model.port.out.persistence.SubscriptionRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class SubscriptionService implements SubscriptionUseCases {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public Subscription addSubscription(CreateSubscriptionCommand command) {
        return subscriptionRepository.save(
                new Subscription(UUID.randomUUID().toString(),
                        command.name(),
                        command.description(),
                        command.features()
                )
        );
    }
}
