package com.operis.project.core.application.report.port.in;

import com.operis.project.core.application.report.model.GetProjectSummaryCommand;
import com.operis.project.core.application.report.model.ProjectSummary;
import com.operis.project.core.application.report.model.ProjectSummaryReport;
import com.operis.project.core.application.report.model.ReportGenerator;

public interface ReportUseCases {

    ProjectSummaryReport generateReport(GetProjectSummaryCommand command,
                                        ReportGenerator<ProjectSummary, ProjectSummaryReport> reportGenerator);
}
