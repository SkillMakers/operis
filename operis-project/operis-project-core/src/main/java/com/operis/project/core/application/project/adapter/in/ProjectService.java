package com.operis.project.core.application.project.adapter.in;

import com.operis.project.core.application.project.model.*;
import com.operis.project.core.application.project.model.exception.NotFoundException;
import com.operis.project.core.application.project.port.in.ProjectUseCases;
import com.operis.project.core.application.project.port.out.persistence.ProjectRepository;
import com.operis.project.core.application.task.model.Task;
import com.operis.project.core.application.task.port.out.persistence.TaskRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class ProjectService implements ProjectUseCases {
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    @Override
    public Project createProject(CreateProjectCommand command) {
        return projectRepository.save(
                new Project(UUID.randomUUID().toString(),
                        command.owner(),
                        command.name(),
                        command.description()
                )
        );
    }

    @Override
    public Project changeProjectName(ChangeProjectNameCommand command) {
        projectRepository.findById(command.projectId())
                .orElseThrow(() -> new NotFoundException("Project not found"));

        return projectRepository.changeProjectName(command.projectId(), command.newName());
    }

    @Override
    public Project changeProjectDescription(ChangeProjectDescriptionCommand command) {
        projectRepository.findById(command.projectId())
                .orElseThrow(() -> new NotFoundException("Project not found"));

        return projectRepository.changeProjectDescription(command.projectId(), command.newDescription());
    }

    @Override
    public void archiveProject(DeleteProjectCommand command) {
        Project foundProject = projectRepository.findById(command.projectId())
                .orElseThrow(() -> new NotFoundException("Project not found"));

        Project updatedProject = new Project(foundProject, true);
        projectRepository.save(updatedProject);
    }

    @Override
    public Project changeProjectMembers(ChangeProjectMembersCommand command) {
        Project foundProject = projectRepository.findById(command.projectId())
                .orElseThrow(() -> new NotFoundException("Project not found"));

        Project updatedProject = new Project(foundProject, command.members());
        return projectRepository.save(updatedProject);
    }

    @Override
    public Project addTaskToProject(AddTaskCommand command) {
        Project foundProject = projectRepository.findById(command.projectId())
                .orElseThrow(() -> new NotFoundException("Project not found"));

        Task task = new Task(UUID.randomUUID().toString(),
                command.title(),
                command.description(),
                foundProject,
                command.owner(),
                command.assignedTo().getUserEmail());

        foundProject.addTask(new ProjectTask(
                task.id(),
                task.title(),
                task.description(),
                task.owner(),
                command.assignedTo()
        ));

        taskRepository.save(task);
        return projectRepository.save(foundProject);
    }

    @Override
    public Project removeTaskFromProject(RemoveTaskCommand command) {
        Project foundProject = projectRepository.findById(command.projectId())
                .orElseThrow(() -> new NotFoundException("Project not found"));

        foundProject.tasks().removeIf(projectTask -> projectTask.id().equals(command.taskId()));
        taskRepository.deleteById(command.taskId());

        return projectRepository.save(foundProject);
    }

    @Override
    public void exportProjectSummary(ExportCommand command) {

    }
}
