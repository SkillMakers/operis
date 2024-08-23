package com.operis.project.core.service.adapter.in.rest.model;

import com.operis.project.core.application.project.model.ChangeProjectMembersCommand;
import com.operis.project.core.application.project.model.ProjectMember;

import java.util.List;

public record ChangeProjectMembersPayload(
        List<String> membersEmails
) {

    public ChangeProjectMembersCommand toCommand(String projectId) {
        return new ChangeProjectMembersCommand(projectId,
                membersEmails.stream().map(ProjectMember::new).toList());
    }
}
