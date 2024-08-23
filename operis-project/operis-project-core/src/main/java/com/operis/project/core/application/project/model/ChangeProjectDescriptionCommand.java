package com.operis.project.core.application.project.model;

public record ChangeProjectDescriptionCommand(String projectId, String newDescription) {

    public ChangeProjectDescriptionCommand {
        if (projectId == null || projectId.isBlank()) {
            throw new IllegalArgumentException("projectId must not be null or blank");
        }
    }
}
