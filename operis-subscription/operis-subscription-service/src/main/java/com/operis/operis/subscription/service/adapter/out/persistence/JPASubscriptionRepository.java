package com.operis.operis.subscription.service.adapter.out.persistence;

import com.operis.subscription.code.model.Subscription;
import com.operis.subscription.code.model.port.out.persistence.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JPASubscriptionRepository implements SubscriptionRepository {

    private final JPASubscriptionSpringDataRepository jpaSubscriptionSpringDataRepository;

    @Override
    public Subscription save(Subscription subscription) {
        SubscriptionEntity subscriptionEntity = SubscriptionEntity.from(subscription);
        jpaSubscriptionSpringDataRepository.save(subscriptionEntity);
        return subscription;
    }

}
