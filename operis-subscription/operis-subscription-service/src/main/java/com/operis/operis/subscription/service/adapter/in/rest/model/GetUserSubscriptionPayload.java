package com.operis.operis.subscription.service.adapter.in.rest.model;

import com.operis.subscription.code.model.model.GetUserSubscriptionsCommand;

public record GetUserSubscriptionPayload(String userEmail) {
    public GetUserSubscriptionsCommand toCommand() {
        return new GetUserSubscriptionsCommand(userEmail);
    }
}
