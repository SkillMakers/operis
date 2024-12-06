package com.operis.subscription.code.model.port.in;

import com.operis.subscription.code.model.Subscription;
import com.operis.subscription.code.model.model.CreateSubscriptionCommand;

import java.util.List;

public interface SubscriptionUseCases {

    Subscription addSubscription(CreateSubscriptionCommand createSubscriptionCommand);

    List<Subscription> getAllSubscriptions();
}
