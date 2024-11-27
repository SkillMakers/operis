package com.operis.operis.subscription.service.adapter.in.rest.model;

import com.operis.subscription.code.model.UserSubscription;

public record UserSubscriptionDto(String userEmail,
                                  SubscriptionDto subscription) {

    public static UserSubscriptionDto from(UserSubscription userSubscription) {
        return userSubscription != null
                ? new UserSubscriptionDto(userSubscription.userEMail(), SubscriptionDto.from(userSubscription.subscription()))
                : null;
    }
}
