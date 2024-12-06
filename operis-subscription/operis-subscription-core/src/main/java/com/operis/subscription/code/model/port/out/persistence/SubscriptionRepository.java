package com.operis.subscription.code.model.port.out.persistence;

import com.operis.subscription.code.model.Subscription;

import java.util.List;

public interface SubscriptionRepository {

    Subscription save(Subscription subscription);

    Subscription findById(String subscriptionId);

    List<Subscription> findAll();
}
