package com.operis.project.core.application.report.model;

import com.operis.project.core.application.task.model.TaskStatus;

import java.time.LocalDate;

public record GetProjectSummaryCommand(String projectId, TaskStatus status, LocalDate from, LocalDate to) {

    public GetProjectSummaryCommand {
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("from must not be after to");
        }
    }

    boolean exportAllStatus() {
        return status == null;
    }

    boolean exportAllDates() {
        return from == null && to == null;
    }
}
