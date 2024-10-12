package com.operis.project.core.application.report.adapter.in;

import com.operis.project.core.application.project.model.Project;
import com.operis.project.core.application.project.model.exception.ProjectNotFoundException;
import com.operis.project.core.application.project.port.out.persistence.ProjectRepository;
import com.operis.project.core.application.report.model.GetProjectSummaryCommand;
import com.operis.project.core.application.report.model.ProjectSummary;
import com.operis.project.core.application.report.port.in.ReportUseCases;
import com.operis.project.core.application.task.model.Task;
import com.operis.project.core.application.task.model.TaskCriteria;
import com.operis.project.core.application.task.port.out.persistence.TaskRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ReportService implements ReportUseCases {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    @Override
    public ProjectSummary getProjectSummary(GetProjectSummaryCommand command) {
        Project project = projectRepository.findById(command.projectId(), false)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        List<Task> summaryTasks = taskRepository.getTasks(
                new TaskCriteria(command.projectId(), command.status(), command.from(), command.to())

        );
        return new ProjectSummary(
                project.name(),
                project.description(),
                project.owner(),
                summaryTasks
        );
    }
}
