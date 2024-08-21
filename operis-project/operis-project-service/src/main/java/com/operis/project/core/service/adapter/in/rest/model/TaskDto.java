package com.operis.project.core.service.adapter.in.rest.model;

import com.operis.project.core.application.project.model.ProjectTask;
import com.operis.project.core.application.task.model.TaskStatus;

import java.util.List;

public record TaskDto(
        String id,
        String title,
        String description,
        String ownerId,
        String assigneeId,
        TaskStatus status,
        List<TaskHistoryDto> history,
        String projectId
) {

    public static TaskDto from(ProjectTask task, String projectId) {
        return new TaskDto(
                task.id(),
                task.title(),
                task.description(),
                task.owner().userEmail(), // user email
                task.assignedTo().getUserEmail(), // user email
                task.status(),
                List.of(),
                projectId
        );
    }
}
