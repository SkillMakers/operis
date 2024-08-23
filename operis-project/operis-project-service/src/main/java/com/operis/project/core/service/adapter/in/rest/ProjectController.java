package com.operis.project.core.service.adapter.in.rest;

import com.operis.project.core.application.project.port.in.ProjectUseCases;
import com.operis.project.core.service.adapter.in.rest.helper.JWTTokenService;
import com.operis.project.core.service.adapter.in.rest.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectUseCases projectUseCases;
    private final JWTTokenService jwtTokenService;

    @PostMapping("")
    public ProjectDto createProject(@RequestBody CreateProjectPayload payload,
                                    @RequestHeader("Authorization") String authorizationHeader) {
        String connectedUserEmail = jwtTokenService.extractUserEmail(authorizationHeader);

        return ProjectDto.from(
                projectUseCases.createProject(payload.toCommand(connectedUserEmail))
        );
    }

    @GetMapping("")
    public List<ProjectDto> getAllProjects() {
        return projectUseCases.getAllProjects().stream()
                .map(ProjectDto::from)
                .toList();
    }

    @PutMapping("/{projectId}/name")
    public ProjectDto changeProjectName(
            @PathVariable("projectId") String projectId,
            @RequestBody ChangeProjectNamePayload payload) {
        return ProjectDto.from(
                projectUseCases.changeProjectName(payload.toCommand(projectId))
        );
    }

    @PutMapping("/{projectId}/description")
    public ProjectDto changeProjectDescription(
            @PathVariable("projectId") String projectId,
            @RequestBody ChangeProjectDescriptionPayload payload) {
        return ProjectDto.from(
                projectUseCases.changeProjectDescription(payload.toCommand(projectId))
        );
    }

    @PutMapping("/{projectId}/tasks")
    public ProjectDto addTaskToProject(@PathVariable("projectId") String projectId,
                                       @RequestBody AddTaskToProjectPayload payload,
                                       @RequestHeader("Authorization") String authorizationHeader) {
        String connectedUserEmail = jwtTokenService.extractUserEmail(authorizationHeader);
        return ProjectDto.from(
                projectUseCases.addTaskToProject(payload.toCommand(projectId, connectedUserEmail))
        );
    }

    @PutMapping("/{projectId}/members")
    public ProjectDto changeProjectMembers(@PathVariable("projectId") String projectId,
                                           @RequestBody ChangeProjectMembersPayload payload) {
        return ProjectDto.from(
                projectUseCases.changeProjectMembers(payload.toCommand(projectId))
        );
    }
}
