package com.operis.operis.subscription.service.adapter.out.persistence.UserSubscription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JPAUserSubscriptionSpringDataRepository extends JpaRepository<UserSubscriptionEntity, String> {

}
