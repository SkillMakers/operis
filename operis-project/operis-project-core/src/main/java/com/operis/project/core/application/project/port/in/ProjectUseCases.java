package com.operis.project.core.application.project.port.in;

import com.operis.project.core.application.project.model.*;

import java.util.List;

public interface ProjectUseCases {
    Project createProject(CreateProjectCommand command);

    List<Project> getAllProjects();

    Project changeProjectName(ChangeProjectNameCommand command);

    Project changeProjectDescription(ChangeProjectDescriptionCommand command);

    void archiveProject(DeleteProjectCommand command);

    Project changeProjectMembers(ChangeProjectMembersCommand command);

    Project addTaskToProject(AddTaskToProjectCommand command);

    Project removeTaskFromProject(RemoveTaskCommand command);

    void exportProjectSummary(ExportCommand command);
}
