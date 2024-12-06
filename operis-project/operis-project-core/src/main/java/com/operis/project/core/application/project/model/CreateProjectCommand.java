package com.operis.project.core.application.project.model;

public record CreateProjectCommand(String name, String description, String ownerEmail) {
}
