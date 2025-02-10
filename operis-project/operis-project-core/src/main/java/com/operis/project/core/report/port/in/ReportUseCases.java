package com.operis.project.core.report.port.in;

import com.operis.project.core.report.model.GetProjectSummaryCommand;
import com.operis.project.core.report.model.ProjectSummary;
import com.operis.project.core.report.model.ProjectSummaryReport;
import com.operis.project.core.report.model.ReportGenerator;

public interface ReportUseCases {

    ProjectSummaryReport generateReport(GetProjectSummaryCommand command,
                                        ReportGenerator<ProjectSummary, ProjectSummaryReport> reportGenerator);
}
