package com.operis.service;

import com.nimbusds.jwt.JWTClaimsSet;
import com.operis.dto.ProjectDto;
import com.operis.mapper.ProjectMapper;
import com.operis.project.core.application.Project;
import com.operis.repository.ProjectRepository;
import com.operis.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ProjectMapper projectMapper;

    public ProjectDto createProject(String token, ProjectDto projectDto) {

        JWTClaimsSet jwtClaimsSet = jwtTokenUtil.parseToken(token);
        String email = jwtClaimsSet.getSubject();

        // Convert DTO to entity
        Project project = projectMapper.projectDtoToProject(projectDto);

        // Set the owner and add the owner to members
        project.setOwnerId(email);
        project.setMemberIds(Collections.singletonList(email));

        // Save the project
        Project savedProject = projectRepository.save(project);

        // Convert entity to DTO
        return projectMapper.projectToProjectDto(savedProject);

    }

}
