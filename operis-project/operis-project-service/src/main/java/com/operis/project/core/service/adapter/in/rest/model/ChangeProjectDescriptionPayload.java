package com.operis.project.core.service.adapter.in.rest.model;

import com.operis.project.core.application.project.model.ChangeProjectDescriptionCommand;

public record ChangeProjectDescriptionPayload(
        String projectId,
        String newDescription
) {

    public ChangeProjectDescriptionCommand toCommand() {
        return new ChangeProjectDescriptionCommand(projectId(), newDescription());
    }

}
