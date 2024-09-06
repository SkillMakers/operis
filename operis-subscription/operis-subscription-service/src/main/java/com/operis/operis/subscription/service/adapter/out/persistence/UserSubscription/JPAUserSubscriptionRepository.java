package com.operis.operis.subscription.service.adapter.out.persistence.UserSubscription;

import com.operis.subscription.code.model.UserSubscription;
import com.operis.subscription.code.model.port.out.persistence.UserSubscriptionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JPAUserSubscriptionRepository implements UserSubscriptionRepository {

    private final JPAUserSubscriptionSpringDataRepository jpaUserSubscriptionSpringDataRepository;

    @Override
    @Transactional
    public void save(UserSubscription userSubscription) {
        UserSubscriptionEntity userSubscriptionEntity = UserSubscriptionEntity.from(userSubscription);
        jpaUserSubscriptionSpringDataRepository.save(userSubscriptionEntity);
    }

    @Override
    public void delete(Long userSubscriptionId) {

    }

}
