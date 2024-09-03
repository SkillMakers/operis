package com.operis.project.core.service.adapter.in.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.operis.project.core.application.project.model.*;
import com.operis.project.core.application.project.port.in.ProjectUseCases;
import com.operis.project.core.service.adapter.in.rest.helper.JWTTokenService;
import com.operis.project.core.service.adapter.in.rest.model.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

            when(jwtTokenService.extractUserEmail(Mockito.anyString()))
                    .thenReturn("imad.test@gmail.com");

            when(projectUseCases.createProject(Mockito.any(CreateProjectCommand.class)))
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
    class ChangeProjectName {
        @Test
        void shouldChangeNameOfProject() throws Exception {
            // Given
            ChangeProjectNamePayload changeProjectNamePayload = new ChangeProjectNamePayload(
                    "Create Operis Web Application."
            );

            when(projectUseCases.changeProjectName(Mockito.any(ChangeProjectNameCommand.class)))
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

            when(projectUseCases.changeProjectDescription(Mockito.any(ChangeProjectDescriptionCommand.class)))
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

            when(jwtTokenService.extractUserEmail(Mockito.anyString()))
                    .thenReturn("imad.test@gmail.com");

            when(projectUseCases.addTaskToProject(Mockito.any(AddTaskToProjectCommand.class)))
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
    }

    @Nested
    class ChangeProjectMembers {
        @Test
        void shouldChangeProjectMembers() throws Exception {
            // Given
            ChangeProjectMembersPayload addTaskToProjectPayload = new ChangeProjectMembersPayload(
                    Set.of("imad.test@gmail.com", "ronald.test@gmail.com")
            );

            when(projectUseCases.changeProjectMembers(Mockito.any(ChangeProjectMembersCommand.class)))
                    .thenReturn(new Project("projectId", new ProjectOwner("imad.test@gmail.com"),
                            "Create Operis Web App.",
                            "Create an IT product allowing to manage projects for individuals and companies."));

            // When
            mockMvc.perform(put("/api/projects/projectId/members")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(addTaskToProjectPayload)))
                    // Then
                    .andExpect(status().isOk());
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