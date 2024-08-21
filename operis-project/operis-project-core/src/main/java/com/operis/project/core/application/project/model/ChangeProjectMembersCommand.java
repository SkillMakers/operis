package com.operis.project.core.application.project.model;

import java.util.List;

public record ChangeProjectMembersCommand(String projectId, List<ProjectMember> members) {

    public ChangeProjectMembersCommand {
        if (projectId == null) {
            throw new IllegalArgumentException("projectId must not be null");
        }
    }
}
