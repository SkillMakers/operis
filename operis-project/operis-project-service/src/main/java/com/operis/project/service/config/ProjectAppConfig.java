package com.operis.project.service.config;

import com.operis.project.core.project.adapter.in.ProjectService;
import com.operis.project.core.project.port.in.ProjectUseCases;
import com.operis.project.core.project.port.out.http.UserProfileClient;
import com.operis.project.core.project.port.out.http.UserSubscriptionClient;
import com.operis.project.core.project.port.out.persistence.ProjectRepository;
import com.operis.project.core.report.adapter.in.ReportService;
import com.operis.project.core.report.port.in.ReportUseCases;
import com.operis.project.core.task.port.out.persistence.TaskRepository;
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

    @Autowired
    private UserSubscriptionClient userSubscriptionClient;

    @Bean
    public ProjectUseCases projectService() {
        return new ProjectService(projectRepository, taskRepository, userProfileClient);
    }

    @Bean
    public ReportUseCases reportService() {
        return new ReportService(projectRepository, taskRepository, userSubscriptionClient);
    }
}
