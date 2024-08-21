package com.operis.mapper;

import com.operis.dto.ProjectDto;
import com.operis.project.core.application.Project;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectDto projectToProjectDto(Project project);

    Project projectDtoToProject(ProjectDto projectDto);
}
