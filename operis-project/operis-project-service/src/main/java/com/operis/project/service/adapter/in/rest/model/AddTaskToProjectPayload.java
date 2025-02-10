package com.operis.project.service.adapter.in.rest.model;

import com.operis.project.core.project.model.AddTaskToProjectCommand;
import com.operis.project.core.project.model.ProjectMember;
import com.operis.project.core.task.model.TaskOwner;

public record AddTaskToProjectPayload(
        String title,
        String description,
        String assignedToEmail
) {

    public AddTaskToProjectCommand toCommand(String projectId, String ownerEmail) {
        return new AddTaskToProjectCommand(projectId,
                title(),
                description(),
                new TaskOwner(ownerEmail),
                new ProjectMember(assignedToEmail()));
    }

}
