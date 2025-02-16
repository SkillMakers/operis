package com.operis.project.service.adapter.in.rest;

import com.operis.project.core.project.model.DeleteProjectCommand;
import com.operis.project.core.project.port.in.ProjectUseCases;
import com.operis.project.service.adapter.in.rest.infrastructure.jwt.JWTTokenService;
import com.operis.project.service.adapter.in.rest.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Tag(name = "Project API", description = "Operations for managing projects")
@Slf4j
public class ProjectController {

    private final ProjectUseCases projectUseCases;
    private final JWTTokenService jwtTokenService;

    @PostMapping
    @Operation(summary = "Create a new project", description = "Creates a new project with the provided details.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Project created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request - Invalid payload or missing required fields",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectApiError.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error - Unexpected error occurred",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectApiError.class))
                    )
            })
    public ResponseEntity<ProjectDto> createProject(@Valid @RequestBody CreateProjectPayload payload,
                                                    @Parameter(description = "JWT token for the connected user", hidden = true)
                                                    @RequestHeader("Authorization") String authorizationHeader) {
        String connectedUserEmail = jwtTokenService.extractUserEmail(authorizationHeader);

        var projectDto = ProjectDto.from(
                projectUseCases.createProject(payload.toCommand(connectedUserEmail))
        );

        return new ResponseEntity<>(projectDto, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all projects", description = "Retrieves a list of all projects.")
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        return ResponseEntity.ok(projectUseCases.getAllProjects().stream()
                .map(ProjectDto::from)
                .toList());
    }

    @PutMapping("/{projectId}/name")
    @Operation(summary = "Change project name", description = "Updates the name of an existing project.")
    public ResponseEntity<ProjectDto> changeProjectName(
            @PathVariable("projectId") String projectId,
            @RequestBody ChangeProjectNamePayload payload) {
        return ResponseEntity.ok(ProjectDto.from(
                projectUseCases.changeProjectName(payload.toCommand(projectId))
        ));
    }

    @PutMapping("/{projectId}/description")
    @Operation(summary = "Change project description", description = "Updates the description of an existing project.")
    public ResponseEntity<ProjectDto> changeProjectDescription(
            @PathVariable("projectId") String projectId,
            @RequestBody ChangeProjectDescriptionPayload payload) {
        return ResponseEntity.ok(ProjectDto.from(
                projectUseCases.changeProjectDescription(payload.toCommand(projectId))
        ));
    }

    @PutMapping("/{projectId}/tasks")
    @Operation(summary = "Add a task to project", description = "Adds a new task to an existing project.")
    public ResponseEntity<ProjectDto> addTaskToProject(@PathVariable("projectId") String projectId,
                                                       @RequestBody AddTaskToProjectPayload payload,
                                                       @RequestHeader("Authorization") String authorizationHeader) {
        String connectedUserEmail = jwtTokenService.extractUserEmail(authorizationHeader);
        return ResponseEntity.ok(ProjectDto.from(
                projectUseCases.addTaskToProject(payload.toCommand(projectId, connectedUserEmail))
        ));
    }

    @PutMapping("/{projectId}/members")
    @Operation(summary = "Change project members", description = "Updates the members of an existing project.")
    public ResponseEntity<ProjectDto> changeProjectMembers(@PathVariable("projectId") String projectId,
                                           @RequestBody ChangeProjectMembersPayload payload) {
        return ResponseEntity.ok(ProjectDto.from(
                projectUseCases.changeProjectMembers(payload.toCommand(projectId))
        ));
    }

    @DeleteMapping("/{projectId}")
    @Operation(summary = "Delete project", description = "Archives the project with the specified ID.")
    public void deleteProject(@PathVariable("projectId") String projectId) {
        projectUseCases.archiveProject(new DeleteProjectCommand(projectId));
    }
}
