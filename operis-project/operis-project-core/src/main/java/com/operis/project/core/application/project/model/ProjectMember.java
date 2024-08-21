package com.operis.project.core.application.project.model;

import lombok.Data;

import java.util.Objects;

@Data
public final class ProjectMember {
    private final String userEmail;

    public ProjectMember(String userEmail) {
        this.userEmail = Objects.requireNonNull(userEmail, "userEmail must not be null");
    }
}
