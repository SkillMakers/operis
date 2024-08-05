package com.operis.user.profile.core.service.adapters.outbound.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JPAUserProfileSpringDataRepository extends JpaRepository<UserProfileEntity, Long> {

}
