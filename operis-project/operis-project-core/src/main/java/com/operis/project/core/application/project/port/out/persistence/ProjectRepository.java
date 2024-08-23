package com.operis.project.core.application.project.port.out.persistence;

import com.operis.project.core.application.project.model.Project;
import com.operis.project.core.application.project.model.ProjectMember;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {
    Project save(Project project);

    Project changeProjectName(String projectId, String newName);

    Project changeProjectDescription(String projectId, String newDescription);

    Project changeProjectMembers(String projectId, List<ProjectMember> projectMembers);

    Optional<Project> findById(String id);

    List<Project> findAll();
}
