package com.operis.project.service.adapter.in.rest.model;

import com.operis.project.core.project.model.ChangeProjectDescriptionCommand;

public record ChangeProjectDescriptionPayload(
        String newDescription
) {

    public ChangeProjectDescriptionCommand toCommand(String projectId) {
        return new ChangeProjectDescriptionCommand(projectId, newDescription());
    }

}
