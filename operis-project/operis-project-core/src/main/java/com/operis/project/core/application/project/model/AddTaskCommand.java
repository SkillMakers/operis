package com.operis.project.core.application.project.model;

import com.operis.project.core.application.task.model.TaskOwner;

public record AddTaskCommand(String projectId, String title, String description, TaskOwner owner, ProjectMember assignedTo) {
    public AddTaskCommand {
        if (projectId == null) {
            throw new IllegalArgumentException("projectId must not be null");
        }

        if (owner == null) {
            throw new IllegalArgumentException("owner must not be null");
        }

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title must not be null or blank");
        }
    }
}
