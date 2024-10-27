package com.operis.project.core.application.task.model;

import java.time.LocalDateTime;

public record TaskCriteria(String projectId, TaskStatus status, LocalDateTime from, LocalDateTime to) {

}
