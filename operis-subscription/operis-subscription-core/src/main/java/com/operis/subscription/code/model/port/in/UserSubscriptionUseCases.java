package com.operis.subscription.code.model.port.in;

import com.operis.subscription.code.model.model.SubscribeUserCommand;

public interface UserSubscriptionUseCases {

    void subscribeUser(SubscribeUserCommand subscribeUserCommand);

    void unsubscribe(String userSubscriptionId);
}
