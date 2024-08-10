package com.operis.user.profile.core.service.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JPAUserProfileSpringDataRepository extends JpaRepository<UserProfileEntity, Long> {

    @Query("SELECT p FROM UserProfileEntity p WHERE p.firstName LIKE %:query% OR p.lastName LIKE %:query%")
    List<UserProfileEntity> findByFirstNameOrLastName(@Param("query")  String query);
}
