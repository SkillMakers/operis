package com.operis.dto;

import com.operis.project.core.application.TaskStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskHistoryDto {
    private Long id;
    private TaskStatus status;
    private LocalDateTime timestamp;
}
