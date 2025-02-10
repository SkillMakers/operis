package com.operis.project.service.adapter.in.rest.model;

import com.operis.project.core.project.model.ChangeProjectNameCommand;

public record ChangeProjectNamePayload(
        String newName
) {

    public ChangeProjectNameCommand toCommand(String projectId) {
        return new ChangeProjectNameCommand(projectId, newName());
    }
}
