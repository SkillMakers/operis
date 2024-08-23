package com.operis.subscription.code.model.port.out.persistence;

import com.operis.subscription.code.model.UserSubscription;

public interface UserSubscriptionRepository {

    UserSubscription save(UserSubscription userSubscription);

    void delete(Long userSubscriptionId);
}
