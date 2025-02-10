package com.operis.project.service.adapter.in.rest.model;

import com.operis.project.core.project.model.ChangeProjectMembersCommand;
import com.operis.project.core.project.model.ProjectMember;

import java.util.Set;

public record ChangeProjectMembersPayload(
        Set<String> membersEmails
) {

    public ChangeProjectMembersCommand toCommand(String projectId) {
        return new ChangeProjectMembersCommand(projectId,
                membersEmails.stream().map(ProjectMember::new).toList());
    }
}
