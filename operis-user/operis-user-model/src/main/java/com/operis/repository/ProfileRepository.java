package com.operis.repository;

import com.operis.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Profile findByUserId(Long userId);

    @Query("SELECT p FROM Profile p WHERE p.firstName LIKE %:search% OR p.lastName LIKE %:search%")
    List<Profile> findByFirstNameOrLastName(@Param("search")  String search);
}
