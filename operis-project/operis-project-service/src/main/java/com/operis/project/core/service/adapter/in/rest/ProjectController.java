package com.operis.project.core.service.adapter.in.rest;

import com.operis.project.core.application.project.port.in.ProjectUseCases;
import com.operis.project.core.service.adapter.in.rest.helper.JWTTokenService;
import com.operis.project.core.service.adapter.in.rest.model.ChangeProjectDescriptionPayload;
import com.operis.project.core.service.adapter.in.rest.model.ChangeProjectNamePayload;
import com.operis.project.core.service.adapter.in.rest.model.CreateProjectPayload;
import com.operis.project.core.service.adapter.in.rest.model.ProjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectUseCases projectUseCases;
    private final JWTTokenService jwtTokenService;

    @PostMapping("")
    public ProjectDto create(@RequestBody CreateProjectPayload payload, @RequestHeader("Authorization") String token) {
        String connectedUserEmail = jwtTokenService.extractUserEmail(token);

        return ProjectDto.from(
                projectUseCases.createProject(payload.toCommand(connectedUserEmail))
        );
    }

    @PutMapping("")
    public ProjectDto changeProjectName(@RequestBody ChangeProjectNamePayload payload) {
        return ProjectDto.from(
                projectUseCases.changeProjectName(payload.toCommand())
        );
    }

    @PutMapping("")
    public ProjectDto changeProjectDescription(@RequestBody ChangeProjectDescriptionPayload payload) {
        return ProjectDto.from(
                projectUseCases.changeProjectDescription(payload.toCommand())
        );
    }
}
