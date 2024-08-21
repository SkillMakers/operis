package com.operis.project.core.service.adapter.in.rest.model;

import com.operis.project.core.application.project.model.CreateProjectCommand;
import com.operis.project.core.application.project.model.ProjectOwner;

public record CreateProjectPayload(
        String name,
        String description,
        String assignedTo
) {
    public CreateProjectCommand toCommand(String connectedUserEmail) {
        return new CreateProjectCommand(
                name(),
                description(),
                new ProjectOwner(connectedUserEmail)
        );
    }
}
