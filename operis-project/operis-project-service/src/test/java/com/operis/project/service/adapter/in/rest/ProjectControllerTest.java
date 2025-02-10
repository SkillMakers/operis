package com.operis.project.service.adapter.in.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.operis.project.core.project.model.*;
import com.operis.project.core.project.model.exception.IllegalProjectMemberException;
import com.operis.project.core.project.model.exception.ProjectNotFoundException;
import com.operis.project.core.project.port.in.ProjectUseCases;
import com.operis.project.core.task.model.TaskOwner;
import com.operis.project.core.task.model.TaskStatus;
import com.operis.project.service.adapter.in.rest.infrastructure.jwt.JWTTokenService;
import com.operis.project.service.adapter.in.rest.model.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectUseCases projectUseCases;

    @MockBean
    private JWTTokenService jwtTokenService;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    class CreateProject {
        @Test
        void shouldCreateProject() throws Exception {
            // Given
            CreateProjectPayload createProjectPayload = new CreateProjectPayload(
                    "Create Operis Web App", "Create an IT product allowing to manage projects."
            );

            when(jwtTokenService.extractUserEmail(anyString()))
                    .thenReturn("imad.test@gmail.com");

            when(projectUseCases.createProject(any(CreateProjectCommand.class)))
                    .thenReturn(new Project("projectId", new ProjectOwner("imad.test@gmail.com"),
                            createProjectPayload.name(),
                            createProjectPayload.description()));

            // When
            mockMvc.perform(post("/api/projects")
                            .header("Authorization", "Bearer 1234")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createProjectPayload)))
                    // Then
                    .andExpect(status().isCreated());
        }
    }

    @Nested
    class GetAllProjects {
        @Test
        void shouldGetAllProjects() throws Exception {
            // Given
            when(projectUseCases.getAllProjects())
                    .thenReturn(List.of(
                            new Project("projectId", new ProjectOwner("imad.test@gmail.com"),
                                    "Create Operis Web App",
                                    "Create an IT product allowing to manage projects.",
                                    List.of(new ProjectTask(
                                            "taskId",
                                            "Migrate to last Spring Boot version",
                                            "Migrate all modules to last Spring Boot version",
                                            new TaskOwner("ronald.test@gmail.com"),
                                            new ProjectMember("imad.test@gmail.com"),
                                            TaskStatus.TODO,
                                            LocalDateTime.now())),
                                    List.of(new ProjectMember("imad.test@gmail.com"), new ProjectMember("ronald.test@gmail.com"))
                            )));

            // When
            mockMvc.perform(get("/api/projects")
                            .contentType(MediaType.APPLICATION_JSON))
                    // Then
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id", is("projectId")))
                    .andExpect(jsonPath("$[0].name", is("Create Operis Web App")))
                    .andExpect(jsonPath("$[0].description", is("Create an IT product allowing to manage projects.")))
                    .andExpect(jsonPath("$[0].owner", is("imad.test@gmail.com")))
                    .andExpect(jsonPath("$[0].members", hasItems("ronald.test@gmail.com", "imad.test@gmail.com")))
                    .andExpect(jsonPath("$[0].tasks", hasSize(1)))
                    .andExpect(jsonPath("$[0].tasks[0].projectId", is("projectId")))
                    .andExpect(jsonPath("$[0].tasks[0].id", is("taskId")))
                    .andExpect(jsonPath("$[0].tasks[0].title", is("Migrate to last Spring Boot version")))
                    .andExpect(jsonPath("$[0].tasks[0].description", is("Migrate all modules to last Spring Boot version")))
                    .andExpect(jsonPath("$[0].tasks[0].ownerId", is("ronald.test@gmail.com")))
                    .andExpect(jsonPath("$[0].tasks[0].assigneeId", is("imad.test@gmail.com")))
                    .andExpect(jsonPath("$[0].tasks[0].status", is("TODO")));
        }
    }

    @Nested
    class ChangeProjectName {
        @Test
        void shouldChangeNameOfProject() throws Exception {
            // Given
            ChangeProjectNamePayload changeProjectNamePayload = new ChangeProjectNamePayload(
                    "Create Operis Web Application."
            );

            when(projectUseCases.changeProjectName(any(ChangeProjectNameCommand.class)))
                    .thenReturn(new Project("projectId", new ProjectOwner("imad.test@gmail.com"),
                            changeProjectNamePayload.newName(),
                            "Create an IT product allowing to manage projects."));

            // When
            mockMvc.perform(put("/api/projects/projectId/name")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(changeProjectNamePayload)))
                    // Then
                    .andExpect(status().isOk());
        }
    }

    @Nested
    class ChangeProjectDescription {
        @Test
        void shouldChangeDescriptionOfProject() throws Exception {
            // Given
            ChangeProjectDescriptionPayload changeProjectDescriptionPayload = new ChangeProjectDescriptionPayload(
                    "Create an IT product allowing to manage projects."
            );

            when(projectUseCases.changeProjectDescription(any(ChangeProjectDescriptionCommand.class)))
                    .thenReturn(new Project("projectId", new ProjectOwner("imad.test@gmail.com"),
                            "Create Operis Web App.",
                            "Create an IT product allowing to manage projects for individuals and companies."));

            // When
            mockMvc.perform(put("/api/projects/projectId/description")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(changeProjectDescriptionPayload)))
                    // Then
                    .andExpect(status().isOk());
        }
    }

    @Nested
    class AddTaskToProject {
        @Test
        void shouldAddTaskToProject() throws Exception {
            // Given
            AddTaskToProjectPayload addTaskToProjectPayload = new AddTaskToProjectPayload(
                    "Migrate to last Spring boot version.", "Desc", "imad.test@gmail.com"
            );

            when(jwtTokenService.extractUserEmail(anyString()))
                    .thenReturn("imad.test@gmail.com");

            when(projectUseCases.addTaskToProject(any(AddTaskToProjectCommand.class)))
                    .thenReturn(new Project("projectId", new ProjectOwner("imad.test@gmail.com"),
                            "Create Operis Web App.",
                            "Create an IT product allowing to manage projects for individuals and companies."));

            // When
            mockMvc.perform(put("/api/projects/projectId/tasks")
                            .header("Authorization", "Bearer 1234")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(addTaskToProjectPayload)))
                    // Then
                    .andExpect(status().isOk());
        }

        @Test
        void shouldReturnNotFoundHttpErrorWhenTheProjectDoesntExist() throws Exception {
            // Given
            AddTaskToProjectPayload addTaskToProjectPayload = new AddTaskToProjectPayload(
                    "Migrate to last Spring boot version.", "Desc", "imad.test@gmail.com"
            );

            when(jwtTokenService.extractUserEmail(anyString())).thenReturn("imad.test@gmail.com");

            when(projectUseCases.addTaskToProject(any(AddTaskToProjectCommand.class)))
                    .thenThrow(new ProjectNotFoundException("Project not found"));

            // When
            mockMvc.perform(put("/api/projects/projectId/tasks")
                            .header("Authorization", "Bearer 1234")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(addTaskToProjectPayload)))
                    // Then
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.statusCode", is(NOT_FOUND.value())))
                    .andExpect(jsonPath("$.message", is("Project not found")));
        }

        @Test
        void shouldReturnConflictHttpErrorWhenTheTaskIsAssignedToSomeoneWhoIsNotPartOfTheProject() throws Exception {
            // Given
            AddTaskToProjectPayload addTaskToProjectPayload = new AddTaskToProjectPayload(
                    "Migrate to last Spring boot version.", "Desc", "imad.test@gmail.com"
            );

            when(jwtTokenService.extractUserEmail(anyString())).thenReturn("imad.test@gmail.com");

            when(projectUseCases.addTaskToProject(any(AddTaskToProjectCommand.class)))
                    .thenThrow(new IllegalProjectMemberException("Task cannot be assigned to a non-member"));

            // When
            mockMvc.perform(put("/api/projects/projectId/tasks")
                            .header("Authorization", "Bearer 1234")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(addTaskToProjectPayload)))
                    // Then
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.statusCode", is(CONFLICT.value())))
                    .andExpect(jsonPath("$.message", is("Task cannot be assigned to a non-member")));
        }

        @Test
        void shouldReturnConflictHttpErrorWhenTheTaskIsCreatedBySomeoneWhoIsNotAMemberOfTheProject
                () throws Exception {
            // Given
            AddTaskToProjectPayload addTaskToProjectPayload = new AddTaskToProjectPayload(
                    "Migrate to last Spring boot version.", "Desc", "imad.test@gmail.com"
            );

            when(jwtTokenService.extractUserEmail(anyString())).thenReturn("ronald.test@gmail.com");

            when(projectUseCases.addTaskToProject(any(AddTaskToProjectCommand.class)))
                    .thenThrow(new IllegalProjectMemberException("Task cannot be created by a non-member"));

            // When
            mockMvc.perform(put("/api/projects/projectId/tasks")
                            .header("Authorization", "Bearer 1234")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(addTaskToProjectPayload)))
                    // Then
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.statusCode", is(CONFLICT.value())))
                    .andExpect(jsonPath("$.message", is("Task cannot be created by a non-member")));
        }
    }

    @Nested
    class ChangeProjectMembers {
        @Test
        void shouldChangeProjectMembers() throws Exception {
            // Given
            ChangeProjectMembersPayload changeProjectMembersPayload = new ChangeProjectMembersPayload(
                    Set.of("imad.test@gmail.com", "ronald.test@gmail.com")
            );

            when(projectUseCases.changeProjectMembers(any(ChangeProjectMembersCommand.class)))
                    .thenReturn(new Project("projectId", new ProjectOwner("imad.test@gmail.com"),
                            "Create Operis Web App.",
                            "Create an IT product allowing to manage projects for individuals and companies."));

            // When
            mockMvc.perform(put("/api/projects/projectId/members")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(changeProjectMembersPayload)))
                    // Then
                    .andExpect(status().isOk());
        }

        @Test
        void shouldReturnConflictHttpErrorWhenAtLeastAMemberDoesntExist() throws Exception {
            // Given
            ChangeProjectMembersPayload changeProjectMembersPayload = new ChangeProjectMembersPayload(
                    Set.of("imad.test@gmail.com", "ronald.test@gmail.com")
            );

            when(projectUseCases.changeProjectMembers(any(ChangeProjectMembersCommand.class)))
                    .thenThrow(new IllegalProjectMemberException("Some members do not have an user account : [imad.test@gmail.com]"));

            // When
            mockMvc.perform(put("/api/projects/projectId/members")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(changeProjectMembersPayload)))
                    // Then
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.statusCode", is(CONFLICT.value())))
                    .andExpect(jsonPath("$.message", is("Some members do not have an user account : [imad.test@gmail.com]")));
        }

    }

    @Nested
    class DeleteProject {
        @Test
        void shouldDeleteProject() throws Exception {
            // When
            mockMvc.perform(delete("/api/projects/projectId"))
                    // Then
                    .andExpect(status().isOk());
        }
    }
}