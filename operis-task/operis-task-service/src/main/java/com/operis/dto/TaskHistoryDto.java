package com.operis.dto;

import lombok.Data;
import com.operis.model.TaskStatus;

import java.time.LocalDateTime;

@Data
public class TaskHistoryDto {
    private Long id;
    private TaskStatus status;
    private LocalDateTime timestamp;
}
