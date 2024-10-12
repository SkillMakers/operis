package com.operis.project.core.application.report.port.in;

import com.operis.project.core.application.report.model.GetProjectSummaryCommand;
import com.operis.project.core.application.report.model.ProjectSummary;

public interface ReportUseCases {

    ProjectSummary getProjectSummary(GetProjectSummaryCommand command);
}
