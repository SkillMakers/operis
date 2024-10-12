package com.operis.project.core.application.task.model;

import java.time.LocalDate;

public record TaskCriteria(String projectId, TaskStatus status, LocalDate from, LocalDate to) {

}
