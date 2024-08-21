package com.operis.project.core.service.adapter.out.persistence.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JPATaskSpringDataRepository extends JpaRepository<TaskEntity, String> {

}
