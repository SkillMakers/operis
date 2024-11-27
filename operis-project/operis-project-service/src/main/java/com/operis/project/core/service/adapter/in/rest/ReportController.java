package com.operis.project.core.service.adapter.in.rest;

import com.operis.project.core.application.report.model.GetProjectSummaryCommand;
import com.operis.project.core.application.report.model.ProjectSummaryReport;
import com.operis.project.core.application.task.model.TaskStatus;
import com.operis.project.core.service.adapter.in.rest.service.ExportFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ReportController {

    private final ExportFileService exportFileService;

    @GetMapping("/{projectId}/export")
    public ResponseEntity<ByteArrayResource> getAllProjects(
            @PathVariable("projectId") String projectId,
            @RequestParam("taskStatus") TaskStatus taskStatus,
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        ProjectSummaryReport projectSummaryReport = exportFileService.exportTasksToCsv(new GetProjectSummaryCommand(projectId, projectId, taskStatus, from, to));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + projectSummaryReport.fileName() + "\"")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(projectSummaryReport.resource());
    }
}
