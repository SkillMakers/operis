package com.operis.project.core.application.project.adapter.in;

import com.operis.project.core.application.project.model.*;
import com.operis.project.core.application.project.model.exception.NotFoundException;
import com.operis.project.core.application.project.port.in.ProjectUseCases;
import com.operis.project.core.application.project.port.out.http.UserProfileClient;
import com.operis.project.core.application.project.port.out.persistence.ProjectRepository;
import com.operis.project.core.application.task.model.Task;
import com.operis.project.core.application.task.port.out.persistence.TaskRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class ProjectService implements ProjectUseCases {
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final UserProfileClient userProfileClient;

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
    public List<Project> getAllProjects() {
        return projectRepository.findAll(new ProjectCriteria(false));
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
        projectRepository.findById(command.projectId())
                .orElseThrow(() -> new NotFoundException("Project not found"));

        projectRepository.archiveProject(command.projectId());
    }

    @Override
    public Project changeProjectMembers(ChangeProjectMembersCommand command) {
        Project foundProject = projectRepository.findById(command.projectId())
                .orElseThrow(() -> new NotFoundException("Project not found"));

        if (command.hasMembers()) {
            List<String> projectMembersEmails = command.getMembersEmails();
            List<Member> existingMembersUserAccounts = userProfileClient.find(new GetUserProfilesFromEmailsPayload(projectMembersEmails));
            List<String> existingUserEmails = Member.getUserEmails(existingMembersUserAccounts);

            List<String> difference = new ArrayList<>(projectMembersEmails);
            difference.removeAll(existingUserEmails);

            if (!difference.isEmpty()) {
                throw new NotFoundException("Some members do not have an user account : %s"
                        .formatted(difference));
            }
        }

        Project updatedProject = new Project(foundProject, command.members());
        return projectRepository.changeProjectMembers(command.projectId(), updatedProject.members());
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

        if (!foundProject.isMember(new ProjectMember(task.assignedTo()))) {
            throw new IllegalArgumentException("Task cannot be assigned to a non-member");
        }

        taskRepository.save(task);

        return foundProject.copyAppendingTask(new ProjectTask(
                task.id(),
                task.title(),
                task.description(),
                task.owner(),
                command.assignedTo()
        ));
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
