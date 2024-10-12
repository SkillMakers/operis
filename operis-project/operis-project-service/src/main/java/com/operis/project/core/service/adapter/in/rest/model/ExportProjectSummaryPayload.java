package com.operis.project.core.service.adapter.in.rest.model;

import com.operis.project.core.application.report.model.GetProjectSummaryCommand;
import com.operis.project.core.application.task.model.TaskStatus;

import java.time.LocalDate;

public record ExportProjectSummaryPayload(
        TaskStatus status, LocalDate from, LocalDate to
) {

    public GetProjectSummaryCommand toCommand(String projectId) {
        return new GetProjectSummaryCommand(projectId, status, from, to);
    }

}
