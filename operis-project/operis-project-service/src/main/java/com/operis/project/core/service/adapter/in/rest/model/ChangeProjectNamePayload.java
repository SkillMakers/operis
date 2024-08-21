package com.operis.project.core.service.adapter.in.rest.model;

import com.operis.project.core.application.project.model.ChangeProjectNameCommand;

public record ChangeProjectNamePayload(
        String projectId,
        String newName
) {

    public ChangeProjectNameCommand toCommand() {
        return new ChangeProjectNameCommand(projectId(), newName());
    }
}
