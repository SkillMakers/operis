package com.operis.subscription.code.model;

import com.operis.subscription.code.model.utils.ValidatorUtils;

import java.util.Objects;

public record UserSubscription(
        String id,
        String userEMail,
        Subscription subscription
) {

    public UserSubscription(String id, String userEMail, Subscription subscription) {
        this.id = ValidatorUtils.validateNonEmpty(id, "ID cannot be null or empty");
        this.userEMail = ValidatorUtils.validateNonEmpty(userEMail, "User mail cannot be null or empty");
        this.subscription = Objects.requireNonNull(subscription, "The subscription cannot be null");

    }

}
