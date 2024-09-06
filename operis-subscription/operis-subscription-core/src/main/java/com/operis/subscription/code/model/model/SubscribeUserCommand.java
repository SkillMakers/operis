package com.operis.subscription.code.model.model;

public record SubscribeUserCommand(String userEMail, String subscriptionId) {

    public SubscribeUserCommand {
        if (userEMail == null || userEMail.isBlank()) {
            throw new IllegalArgumentException("User EMail must not be null or blank");
        }

        if (subscriptionId == null || subscriptionId.isBlank()) {
            throw new IllegalArgumentException("Subscription Id must not be null or blank");
        }
    }
}
