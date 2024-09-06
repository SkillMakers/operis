package com.operis.operis.subscription.service.adapter.in.rest.model;

import com.operis.subscription.code.model.model.SubscribeUserCommand;

public record CreateUserSubscriptionPayloadRecord(String userEmail, String subscriptionId) {

    public SubscribeUserCommand toCommand() {
        return new SubscribeUserCommand(
                userEmail,
                subscriptionId
        );
    }

}
