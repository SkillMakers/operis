package com.operis.project.core.service.adapter.in.rest.model;

import com.operis.project.core.application.project.model.AddTaskCommand;
import com.operis.project.core.application.project.model.ProjectMember;
import com.operis.project.core.application.task.model.TaskOwner;

public record AddTaskToProjectPayload(
        String title,
        String description,
        String assignedToEmail
) {

    public AddTaskCommand toCommand(String projectId, String ownerEmail) {
        return new AddTaskCommand(projectId,
                title(),
                description(),
                new TaskOwner(ownerEmail),
                new ProjectMember(assignedToEmail()));
    }

}
