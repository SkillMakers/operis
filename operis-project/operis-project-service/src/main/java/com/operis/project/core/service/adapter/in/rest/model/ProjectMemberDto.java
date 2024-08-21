package com.operis.project.core.service.adapter.in.rest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.operis.project.core.application.project.model.Project;
import com.operis.project.core.application.project.model.ProjectMember;
import com.operis.project.core.application.project.model.ProjectTask;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProjectMemberDto(
        String userEmail,
        String firstName,
        String lastName
) {

    public static ProjectMemberDto from(Project domain) {
        return new ProjectMemberDto(
                domain.id(),
                domain.name(),
                domain.description(),
                domain.owner().userEmail(),
                domain.members().stream().map(ProjectMember::getUserEmail).toList(),
                domain.archived(),
                domain.tasks().stream().map((ProjectTask task) -> TaskDto.from(task, domain.id())).toList()

        );
    }
}
