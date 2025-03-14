package com.operis.project.service.adapter.out.persistence.project;

import com.operis.project.core.project.model.Project;
import com.operis.project.core.project.model.ProjectCriteria;
import com.operis.project.core.project.model.ProjectMember;
import com.operis.project.core.project.port.out.persistence.ProjectRepository;
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
    public Project changeProjectMembers(String projectId, List<ProjectMember> projectMembers) {
        ProjectEntity projectEntity = jpaProjectSpringDataRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        projectEntity.getMembersEmails().clear();
        projectEntity.getMembersEmails().addAll(ProjectMember.getUserEmails(projectMembers));

        jpaProjectSpringDataRepository.save(projectEntity);

        return projectEntity.toDomain();
    }

    @Transactional
    @Override
    public void archiveProject(String projectId) {
        jpaProjectSpringDataRepository.archiveProject(projectId);
    }

    @Override
    public Optional<Project> findById(String id, boolean archived) {
        return jpaProjectSpringDataRepository.findByIdAndArchived(id, archived)
                .map(ProjectEntity::toDomain);
    }

    @Override
    public List<Project> findAll(ProjectCriteria criteria) {
        return jpaProjectSpringDataRepository.findAllByArchivedFalse().stream()
                .filter(entity -> entity.isArchived() == criteria.archived())
                .map(ProjectEntity::toDomain)
                .toList();
    }
}
