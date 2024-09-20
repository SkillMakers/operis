package com.operis.project.core.service.adapter.out.persistence.project;

import jakarta.ws.rs.QueryParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JPAProjectSpringDataRepository extends JpaRepository<ProjectEntity, String> {

    @Modifying(clearAutomatically = true)
    @Query("update ProjectEntity p set p.name = :newName where p.id = :projectId")
    void changeProjectName(@QueryParam("projectId") String projectId, @QueryParam("newName") String newName);

    @Modifying(clearAutomatically = true)
    @Query("update ProjectEntity p set p.description = :newDescription where p.id = :projectId")
    void changeProjectDescription(@QueryParam("projectId") String projectId, @QueryParam("newDescription") String newDescription);

    @Modifying(clearAutomatically = true)
    @Query("update ProjectEntity p set p.archived = true where p.id = :projectId")
    void archiveProject(String projectId);

    Optional<ProjectEntity> findByIdAndArchived(String id, boolean archived);

    List<ProjectEntity> findAllByArchivedFalse();
}
