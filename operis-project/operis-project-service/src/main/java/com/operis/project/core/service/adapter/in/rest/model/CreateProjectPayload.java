package com.operis.project.core.service.adapter.in.rest.model;

import com.operis.project.core.application.project.model.CreateProjectCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreateProjectPayload(
        @Schema(description = "Project name", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank String name,
        @Schema(description = "Project description", requiredMode = Schema.RequiredMode.NOT_REQUIRED) String description
) {
    public CreateProjectCommand toCommand(String connectedUserEmail) {
        return new CreateProjectCommand(
                name(),
                description(),
                connectedUserEmail
        );
    }
}
