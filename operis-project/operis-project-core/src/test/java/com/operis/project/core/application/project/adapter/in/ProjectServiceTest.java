package com.operis.project.core.application.project.adapter.in;

import com.operis.project.core.application.project.model.*;
import com.operis.project.core.application.project.model.exception.NotFoundException;
import com.operis.project.core.application.project.port.out.http.UserProfileClient;
import com.operis.project.core.application.project.port.out.persistence.ProjectRepository;
import com.operis.project.core.application.task.model.Task;
import com.operis.project.core.application.task.model.TaskOwner;
import com.operis.project.core.application.task.model.TaskStatus;
import com.operis.project.core.application.task.port.out.persistence.TaskRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserProfileClient userProfileClient;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private ProjectService projectService;

    @Nested
    class CreateProject {
        @Test
        void shouldCreateProjectAndAddOwnerToMembers() {
            // Given
            var project = new CreateProjectCommand(
                    "Project name",
                    "Project description",
                    new ProjectOwner("ronald.test@gmail.com")
            );

            // When
            projectService.createProject(project);

            // Then
            ArgumentCaptor<Project> projectArgumentCaptor = ArgumentCaptor.forClass(Project.class);
            verify(projectRepository).save(projectArgumentCaptor.capture());
            Project projectArgument = projectArgumentCaptor.getValue();
            assertAll(
                    () -> assertNotNull(projectArgument),
                    () -> assertNotNull(projectArgument.id()),
                    () -> assertEquals("Project name", projectArgument.name()),
                    () -> assertEquals("Project description", projectArgument.description()),
                    () -> assertEquals("ronald.test@gmail.com", projectArgument.owner().userEmail()),
                    () -> assertThatList(projectArgument.members()).containsExactlyInAnyOrder(new ProjectMember("ronald.test@gmail.com")),
                    () -> assertNotNull(projectArgument.createdAt())
            );
        }
    }

    @Nested
    class ChangeProjectName {
        @Test
        void shouldChangeProjectName() {
            // Given
            String projectId = "123456";
            var project = new Project(
                    projectId,
                    new ProjectOwner("ronald.test@gmail.com"),
                    "Project name",
                    "Project description"
            );

            when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

            // When
            projectService.changeProjectName(
                    new ChangeProjectNameCommand(projectId, "Project name updated"));

            // Then
            verify(projectRepository).changeProjectName(eq(projectId), eq("Project name updated"));
        }

        @Test
        void shouldThrowExceptionWhenProjectNotFound() {
            // Given
            String projectId = "123456";

            when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

            // When
            assertThrows(
                    NotFoundException.class,
                    () -> projectService.changeProjectName(
                            new ChangeProjectNameCommand(projectId, "Project name updated")
                    )
            );

            // Then
            verify(projectRepository, never()).save(any(Project.class));
        }
    }

    @Nested
    class ChangeProjectDescription {
        @Test
        void shouldChangeProjectDescription() {
            // Given
            String projectId = "123456";
            var project = new Project(
                    projectId,
                    new ProjectOwner("ronald.test@gmail.com"),
                    "Project name",
                    "Project description"
            );

            when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

            // When
            projectService.changeProjectDescription(
                    new ChangeProjectDescriptionCommand(projectId, "new Description of project"));

            // Then
            verify(projectRepository).changeProjectDescription(eq(projectId), eq("new Description of project"));
        }

        @Test
        void shouldThrowExceptionWhenProjectNotFound() {
            // Given
            String projectId = "123456";

            when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

            // When
            assertThrows(
                    NotFoundException.class,
                    () -> projectService.changeProjectDescription(
                            new ChangeProjectDescriptionCommand(projectId, "new Description of project")
                    )
            );

            // Then
            verify(projectRepository, never()).save(any(Project.class));
        }
    }

    @Nested
    class ArchiveProject {
        @Test
        void shouldArchiveProject() {
            // Given
            var project = new Project(
                    "123456",
                    new ProjectOwner("ronald.test@gmail.com"),
                    "Project name",
                    "Project description"
            );

            when(projectRepository.findById(project.id())).thenReturn(Optional.of(project));

            // When
            projectService.archiveProject(new DeleteProjectCommand("123456"));

            // Then
            verify(projectRepository).archiveProject(eq(project.id()));
        }

        @Test
        void shouldThrowExceptionWhenProjectNotFound() {
            // Given
            String projectId = "123456";

            when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

            // When
            assertThrows(NotFoundException.class, () -> projectService.archiveProject(new DeleteProjectCommand(projectId)));

            // Then
            verify(projectRepository, never()).save(any(Project.class));
        }
    }

    @Nested
    class ChangeProjectMembers {
        @Test
        void shouldChangeProjectMembersAndKeepOwnerInMembers() {
            // Given
            String projectId = "123456";
            var project = new Project(
                    projectId,
                    new ProjectOwner("ronald.test@gmail.com"),
                    "Project name",
                    "Project description"
            );
            List<ProjectMember> projectMembers = List.of(
                    new ProjectMember("imad.test@gmail.com"),
                    new ProjectMember("charles.test@gmail.com")
            );

            when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
            when(userProfileClient.find(any())).thenReturn(List.of(
                    new Member("imad.test@gmail.com", "Imad", "Test"),
                    new Member("charles.test@gmail.com", "Charles", "Test")
            ));

            // When
            projectService.changeProjectMembers(new ChangeProjectMembersCommand(projectId, projectMembers));

            // Then
            verify(projectRepository).changeProjectMembers(eq(projectId), eq(List.of(
                    new ProjectMember("imad.test@gmail.com"),
                    new ProjectMember("charles.test@gmail.com"),
                    new ProjectMember("ronald.test@gmail.com"))
            ));
        }

        @Test
        void shouldThrowErrorWhenMembersIsNull() {
            // Given
            String projectId = "123456";
            var project = new Project(
                    projectId,
                    new ProjectOwner("ronald.test@gmail.com"),
                    "Project name",
                    "Project description"
            );
            when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

            // When
            assertThrows(NullPointerException.class, () -> projectService.changeProjectMembers(new ChangeProjectMembersCommand(projectId, null)));

            // Then
            verify(projectRepository, never()).save(any(Project.class));
        }

        @Test
        void shouldThrowExceptionWhenProjectNotFound() {
            // Given
            String projectId = "123456";
            List<ProjectMember> projectMembers = List.of(
                    new ProjectMember("imad.test@gmail.com"),
                    new ProjectMember("charles.test@gmail.com")
            );

            when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

            // When
            assertThrows(NotFoundException.class, () -> projectService.changeProjectMembers(new ChangeProjectMembersCommand(projectId, projectMembers)));

            // Then
            verify(projectRepository, never()).save(any(Project.class));
        }
    }

    @Nested
    class AddTaskToProject {
        @Test
        void shouldAddTaskToProjectAndAssignItToAProjectMember() {
            // Given
            String projectId = "123456";
            var project = new Project(
                    projectId,
                    new ProjectOwner("ronald.test@gmail.com"),
                    "Project name",
                    "Project description"
            );

            when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

            // When
            projectService.addTaskToProject(new AddTaskCommand(
                    projectId,
                    "Create database migration script",
                    "Create database migration script for Operis project",
                    new TaskOwner("ronald.test@gmail.com"),
                    new ProjectMember("ronald.test@gmail.com")
            ));

            // Then
            ArgumentCaptor<Task> projectArgumentCaptor = ArgumentCaptor.forClass(Task.class);
            verify(taskRepository).save(projectArgumentCaptor.capture());
            Task projectArgument = projectArgumentCaptor.getValue();
            assertThat(projectArgument.id()).isNotNull();
            assertThat(projectArgument.title()).isEqualTo("Create database migration script");
            assertThat(projectArgument.description()).isEqualTo("Create database migration script for Operis project");
            assertThat(projectArgument.owner().userEmail()).isEqualTo("ronald.test@gmail.com");
            assertThat(projectArgument.assignedTo()).isEqualTo("ronald.test@gmail.com");
            assertThat(projectArgument.status()).isEqualTo(TaskStatus.TODO);
            assertThat(projectArgument.createdAt()).isNotNull();
        }

        @Test
        void shouldThrowErrorWhenTheAssignedToIsNotAMemberOfTheProject() {
            // Given
            String projectId = "123456";
            var project = new Project(
                    projectId,
                    new ProjectOwner("ronald.test@gmail.com"),
                    "Project name",
                    "Project description"
            );

            when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

            // When
            assertThrows(
                    IllegalArgumentException.class,
                    () -> projectService.addTaskToProject(new AddTaskCommand(
                            projectId,
                            "Create database migration script",
                            "Create database migration script for Operis project",
                            new TaskOwner("ronald.test@gmail.com"),
                            new ProjectMember("imad.test@gmail.com")
                    )));

            // Then
            verify(taskRepository, never()).save(any(Task.class));
            verify(projectRepository, never()).save(any(Project.class));
        }

        @Test
        void shouldThrowExceptionWhenProjectNotFound() {
            // Given
            String projectId = "123456";

            when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

            // When
            assertThrows(
                    NotFoundException.class,
                    () -> projectService.addTaskToProject(
                            new AddTaskCommand(
                                    projectId,
                                    "Create database migration script",
                                    "Create database migration script for Operis project",
                                    new TaskOwner("ronald.test@gmail.com"),
                                    new ProjectMember("ronald.test@gmail.com")
                            )
                    )
            );

            // Then
            verify(projectRepository, never()).save(any(Project.class));
        }
    }

    @Nested
    class RemoveTaskFromProject {
        @Test
        void shouldRemoveTaskFromProject() {
            // Given
            String projectId = "123456";
            String taskId = "222222";
            var project = new Project(
                    projectId,
                    new ProjectOwner("ronald.test@gmail.com"),
                    "Project name",
                    "Project description"
            );
            project.addTask(new ProjectTask(
                    taskId,
                    "Create database migration script",
                    "Create database migration script for Operis project",
                    new TaskOwner("ronald.test@gmail.com"),
                    new ProjectMember("ronald.test@gmail.com")));

            when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

            // When
            projectService.removeTaskFromProject(new RemoveTaskCommand(projectId, taskId));

            // Then
            verify(taskRepository).deleteById(eq(taskId));
            ArgumentCaptor<Project> projectArgumentCaptor = ArgumentCaptor.forClass(Project.class);
            verify(projectRepository).save(projectArgumentCaptor.capture());
            Project projectArgument = projectArgumentCaptor.getValue();
            assertThat(projectArgument.tasks()).isEmpty();
        }

        @Test
        void shouldThrowExceptionWhenProjectNotFound() {
            // Given
            String projectId = "123456";

            when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

            // When
            assertThrows(
                    NotFoundException.class,
                    () -> projectService.addTaskToProject(
                            new AddTaskCommand(
                                    projectId,
                                    "Create database migration script",
                                    "Create database migration script for Operis project",
                                    new TaskOwner("ronald.test@gmail.com"),
                                    new ProjectMember("ronald.test@gmail.com")
                            )
                    )
            );

            // Then
            verify(projectRepository, never()).save(any(Project.class));
        }
    }
}