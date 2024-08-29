package com.operis.subscription.code.model.port.in;

import com.operis.subscription.code.model.Subscription;
import com.operis.subscription.code.model.model.CreateSubscriptionCommand;

public interface SubscriptionUseCases {

    Subscription addSubscription(CreateSubscriptionCommand createSubscriptionCommand);

}
