package com.operis.subscription.code.model.port.out.persistence;

import com.operis.subscription.code.model.UserSubscription;

import java.util.Optional;

public interface UserSubscriptionRepository {

    void save(UserSubscription userSubscription);

    void delete(String userEmail);

    Optional<UserSubscription> find(String userEmail);
}
