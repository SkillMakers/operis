package com.operis.operis.subscription.service.adapter.out.persistence.UserSubscription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JPAUserSubscriptionSpringDataRepository extends JpaRepository<UserSubscriptionEntity, String> {
    Optional<UserSubscriptionEntity> findByUserEmail(String userEmail);
}
