package com.operis.project.core.service.adapter.out.persistence.project;

import com.operis.project.core.application.project.model.Project;
import com.operis.project.core.application.project.port.out.persistence.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JPAProjectRepository implements ProjectRepository {
    private final JPAProjectSpringDataRepository jpaProjectSpringDataRepository;

    @Override
    public Project save(Project project) {
        ProjectEntity projectEntity = ProjectEntity.from(project);
        jpaProjectSpringDataRepository.save(projectEntity).toDomain();
        return project;
    }

    @Transactional
    @Override
    public Project changeProjectName(String projectId, String newName) {
        jpaProjectSpringDataRepository.changeProjectName(projectId, newName);

        return jpaProjectSpringDataRepository.findById(projectId)
                .map(ProjectEntity::toDomain)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    @Transactional
    @Override
    public Project changeProjectDescription(String projectId, String newDescription) {
        jpaProjectSpringDataRepository.changeProjectDescription(projectId, newDescription);

        return jpaProjectSpringDataRepository.findById(projectId)
                .map(ProjectEntity::toDomain)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    @Override
    public Optional<Project> findById(String id) {
        return jpaProjectSpringDataRepository.findById(id)
                .map(ProjectEntity::toDomain);
    }

    @Override
    public List<Project> findAll() {
        return jpaProjectSpringDataRepository.findAll().stream()
                .map(ProjectEntity::toDomain)
                .toList();
    }
}
