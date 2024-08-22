package com.operis.project.core.service.adapter.in.rest.model;

import com.operis.project.core.application.project.model.ChangeProjectNameCommand;

public record ChangeProjectNamePayload(
        String newName
) {

    public ChangeProjectNameCommand toCommand(String projectId) {
        return new ChangeProjectNameCommand(projectId, newName());
    }
}
