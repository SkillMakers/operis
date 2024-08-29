package com.operis.project.core.service.adapter.in.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.operis.project.core.application.project.model.CreateProjectCommand;
import com.operis.project.core.application.project.model.Project;
import com.operis.project.core.application.project.model.ProjectOwner;
import com.operis.project.core.application.project.port.in.ProjectUseCases;
import com.operis.project.core.service.adapter.in.rest.helper.JWTTokenService;
import com.operis.project.core.service.adapter.in.rest.model.CreateProjectPayload;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    class Create {
        @Test
        void createProject() throws Exception {
            // Given
            CreateProjectPayload createProjectPayload = new CreateProjectPayload(
                    "Create Operis Web App", "Create an IT product allowing to manage projects."
            );

            when(jwtTokenService.extractUserEmail(Mockito.anyString()))
                    .thenReturn("imad.test@gmail.com");

            when(projectUseCases.createProject(Mockito.any(CreateProjectCommand.class)))
                    .thenReturn(new Project("projectId", new ProjectOwner("imad.test@gmail.com\""),
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
}