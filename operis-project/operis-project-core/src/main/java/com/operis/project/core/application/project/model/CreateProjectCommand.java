package com.operis.project.core.application.project.model;

public record CreateProjectCommand(String name, String description, ProjectOwner owner) {

    public CreateProjectCommand {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be null or blank");
        }

        if (owner == null) {
            throw new IllegalArgumentException("owner must not be null");
        }
    }
}
