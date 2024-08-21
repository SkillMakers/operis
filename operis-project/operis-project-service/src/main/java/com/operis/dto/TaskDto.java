package com.operis.dto;

import com.operis.project.core.application.TaskStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private String ownerId;
    private String assigneeId;
    private TaskStatus status;
    private List<TaskHistoryDto> history;
    private Long projectId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
