package com.operis.project.core.service.adapter.out.persistence.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JPATaskSpringDataRepository extends JpaRepository<TaskEntity, String> {

    @Query("SELECT t FROM TaskEntity t WHERE t.project.id = :projectId AND t.status = :status AND t.createdAt BETWEEN :from AND :to")
    List<TaskEntity> findAll(String projectId, TaskStatusEntity status, LocalDateTime from, LocalDateTime to);
}
