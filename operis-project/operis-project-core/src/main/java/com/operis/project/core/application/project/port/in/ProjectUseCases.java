package com.operis.project.core.application.project.port.in;

import com.operis.project.core.application.project.model.*;

public interface ProjectUseCases {
    Project createProject(CreateProjectCommand command);

    Project changeProjectName(ChangeProjectNameCommand command);

    Project changeProjectDescription(ChangeProjectDescriptionCommand command);

    void archiveProject(DeleteProjectCommand command);

    Project changeProjectMembers(ChangeProjectMembersCommand command);

    Project addTaskToProject(AddTaskCommand command);

    Project removeTaskFromProject(RemoveTaskCommand command);

    void exportProjectSummary(ExportCommand command);
}
