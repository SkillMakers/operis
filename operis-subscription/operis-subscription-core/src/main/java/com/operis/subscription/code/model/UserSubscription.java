package com.operis.subscription.code.model;

import java.util.Objects;

public record UserSubscription(
        Long id,
        String userEMail,
        Subscription subscription
) {

    public UserSubscription(String userEMail, Subscription subscription) {
        this(null,
                Objects.requireNonNull(userEMail, "User email cannot be null"),
                Objects.requireNonNull(subscription, "Subscription name cannot be null"));
    }
}
