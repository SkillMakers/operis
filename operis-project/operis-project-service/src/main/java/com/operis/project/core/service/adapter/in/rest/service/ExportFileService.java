package com.operis.project.core.service.adapter.in.rest.service;

import com.opencsv.CSVWriter;
import com.operis.project.core.application.report.model.GetProjectSummaryCommand;
import com.operis.project.core.application.report.model.ProjectSummary;
import com.operis.project.core.application.report.model.ProjectSummaryReport;
import com.operis.project.core.application.report.model.ReportGenerator;
import com.operis.project.core.application.report.port.in.ReportUseCases;
import com.operis.project.core.application.task.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;

@Component
@RequiredArgsConstructor
public class ExportFileService {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = ofPattern("dd/MM/yyyy HH:mm:ss");

    private final ReportUseCases reportUseCases;

    public ProjectSummaryReport exportTasksToCsv(GetProjectSummaryCommand command) {
        return reportUseCases.generateReport(command, generateReportFunction());
    }

    private ReportGenerator<ProjectSummary, ProjectSummaryReport> generateReportFunction() {
        return (projectSummary) -> {
            try (ByteArrayOutputStream out = new ByteArrayOutputStream();
                 OutputStreamWriter writer = new OutputStreamWriter(out);
                 CSVWriter csvWriter = new CSVWriter(writer)) {

                String[] header = {"Project Name", "Title", "Description", "Owner", "Assigned To", "Status", "Created At"};
                csvWriter.writeNext(header);

                List<Task> tasks = projectSummary.tasks();
                for (Task task : tasks) {
                    String[] taskData = {
                            task.project().name(),
                            task.title(),
                            task.description(),
                            task.owner() != null ? task.owner().userEmail() : "",
                            task.assignedTo(),
                            task.status().name(),
                            task.createdAt().format(DATE_TIME_FORMATTER)
                    };
                    csvWriter.writeNext(taskData);
                }

                csvWriter.flush();

                return new ProjectSummaryReport(getFileName(projectSummary.projectName()), new ByteArrayResource(out.toByteArray()));
            } catch (Exception e) {
                throw new RuntimeException("Error while exporting tasks to CSV", e);
            }
        };
    }

    private String getFileName(String projectName) {
        String formattedDate = LocalDateTime.now().format(DATE_TIME_FORMATTER);
        String projectNameWithoutSpaces = projectName.trim().replace(" ", "_");
        return projectNameWithoutSpaces + "_" + formattedDate + ".csv";
    }
}
