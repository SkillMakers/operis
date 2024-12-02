package com.operis.operis.subscription.service.adapter.out.persistence.UserSubscription;

import com.operis.subscription.code.model.UserSubscription;
import com.operis.subscription.code.model.port.out.persistence.UserSubscriptionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
    @Transactional
    public void delete(String userEmail) {
        Optional<UserSubscriptionEntity> userSubscriptionEntity = jpaUserSubscriptionSpringDataRepository.findById(userEmail);
        UserSubscriptionEntity entity = userSubscriptionEntity.orElseThrow(() ->
                new RuntimeException("UserSubscriptionEntity not found for user: " + userEmail)
        );
        jpaUserSubscriptionSpringDataRepository.delete(entity);

    }

    @Override
    public Optional<UserSubscription> find(String userEmail) {
        return jpaUserSubscriptionSpringDataRepository.findByUserEmail(userEmail)
                .map(UserSubscriptionEntity::toDomain);
    }
}
