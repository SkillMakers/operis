package com.operis.project.core.service.config;

import com.operis.project.core.application.project.adapter.in.ProjectService;
import com.operis.project.core.application.project.port.in.ProjectUseCases;
import com.operis.project.core.application.project.port.out.http.UserProfileClient;
import com.operis.project.core.application.project.port.out.persistence.ProjectRepository;
import com.operis.project.core.application.task.port.out.persistence.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectAppConfig {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserProfileClient userProfileClient;

    @Bean
    public ProjectUseCases projectService() {
        return new ProjectService(projectRepository, taskRepository, userProfileClient);
    }
}
