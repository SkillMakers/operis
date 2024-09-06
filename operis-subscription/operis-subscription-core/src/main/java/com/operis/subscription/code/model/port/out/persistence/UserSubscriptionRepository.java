package com.operis.subscription.code.model.port.out.persistence;

import com.operis.subscription.code.model.UserSubscription;

public interface UserSubscriptionRepository {

    void save(UserSubscription userSubscription);

    void delete(Long userSubscriptionId);
}
