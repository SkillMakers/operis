package com.operis.project.core.application.report.model;

import org.springframework.core.io.ByteArrayResource;

public record ProjectSummaryReport(String fileName, ByteArrayResource resource) {

}