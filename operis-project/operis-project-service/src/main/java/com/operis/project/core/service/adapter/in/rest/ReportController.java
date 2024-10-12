package com.operis.project.core.service.adapter.in.rest;

import com.operis.project.core.service.adapter.in.rest.model.ExportProjectSummaryPayload;
import com.operis.project.core.service.adapter.in.rest.service.ExportFileService;
import com.operis.project.core.service.adapter.in.rest.service.ExportFileService.ProjectSummaryReport;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ExportFileService exportFileService;

    @GetMapping("/{projectId}")
    public ResponseEntity<ByteArrayResource> getAllProjects(
            @PathVariable("projectId") String projectId,
            @RequestBody ExportProjectSummaryPayload payload
    ) {
        ProjectSummaryReport projectSummaryReport = exportFileService.exportTasksToCsv(payload.toCommand(projectId));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + projectSummaryReport.fileName() + "\"")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(projectSummaryReport.resource());
    }
}
