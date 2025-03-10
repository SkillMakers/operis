package com.operis.operis.subscription.service.adapter.out.persistence.Subscription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JPASubscriptionSpringDataRepository extends JpaRepository<SubscriptionEntity, String> {

}
