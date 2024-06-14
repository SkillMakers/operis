package com.operis.mapper;

import com.operis.dto.ProjectDto;
import com.operis.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectDto projectToProjectDto(Project project);
    Project projectDtoToProject(ProjectDto projectDto);
}
