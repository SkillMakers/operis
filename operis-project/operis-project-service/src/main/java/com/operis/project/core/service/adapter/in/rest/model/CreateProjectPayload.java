package com.operis.project.core.service.adapter.in.rest.model;

import com.operis.project.core.application.project.model.CreateProjectCommand;
import com.operis.project.core.application.project.model.ProjectOwner;
import jakarta.validation.constraints.NotBlank;

public record CreateProjectPayload(
        @NotBlank(message = "The name must not be null or empty") String name,
        String description
) {
    public CreateProjectCommand toCommand(String connectedUserEmail) {
        return new CreateProjectCommand(
                name(),
                description(),
                new ProjectOwner(connectedUserEmail)
        );
    }
}
