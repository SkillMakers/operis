package com.operis.project.core.application.task.model;

import java.time.LocalDateTime;

public record Task(
        String id,
        String title,
        String description,
        TaskOwner owner,
        String assignedTo,
        TaskStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public Task(String id, String title, String description, TaskOwner owner, String assignedTo) {
        this(
                id,
                title,
                description,
                owner,
                assignedTo,
                TaskStatus.TODO,
                LocalDateTime.now(),
                null
        );
    }
}
