package com.operis.project.core.service.adapter.in.rest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.operis.project.core.application.project.model.Project;
import com.operis.project.core.application.project.model.ProjectMember;
import com.operis.project.core.application.project.model.ProjectTask;
import org.springframework.util.CollectionUtils;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProjectDto(
        String id,
        String name,
        String description,
        String owner, // owner email
        List<String> members, // members emails
        List<TaskDto> tasks
) {

    public static List<ProjectDto> toDtoList(List<Project> domains) {
        return CollectionUtils.isEmpty(domains) ? List.of() : domains.stream()
                .map(ProjectDto::from)
                .toList();
    }

    public static ProjectDto from(Project domain) {
        return new ProjectDto(
                domain.id(),
                domain.name(),
                domain.description(),
                domain.owner().userEmail(),
                domain.members().stream().map(ProjectMember::getUserEmail).toList(),
                domain.tasks().stream().map((ProjectTask task) -> TaskDto.from(task, domain.id())).toList()

        );
    }
}
