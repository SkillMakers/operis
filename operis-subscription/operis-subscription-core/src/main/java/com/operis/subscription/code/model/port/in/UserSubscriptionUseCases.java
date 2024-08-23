package com.operis.subscription.code.model.port.in;

import com.operis.subscription.code.model.UserSubscription;

public interface UserSubscriptionUseCases {

    UserSubscription subscribeUser(UserSubscription userSubscription);

    void unsubscribe(Long userSubscriptionId);
}
