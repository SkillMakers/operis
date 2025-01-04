package com.operis.project.core.service.adapter.in.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.operis.project.core.service.adapter.in.rest.model.CreateProjectPayload;
import com.operis.project.core.service.adapter.in.rest.model.ProjectApiError;
import com.operis.project.core.service.adapter.in.rest.model.ProjectDto;
import com.operis.project.core.service.adapter.out.persistence.project.JPAProjectSpringDataRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProjectControllerIntegrationTest {

    public static final String BEARER_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MjQ0MzM0ODUsInN1YiI6ImltYWQudGVzdEBnbWFpbC5jb20ifQ.3G86W0E_jhiqVKJDioofMIt4dtKzEz9W4ggfR6IM3Zo";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JPAProjectSpringDataRepository jpaProjectRepository;

    @Test
    void createProjectShouldWorksGivenValidParams() throws Exception {
        // GIVEN
        CreateProjectPayload payload =
                new CreateProjectPayload("Operis", "Project management platform");

        // WHEN
        MockHttpServletResponse response = mockMvc.perform(post("/api/projects")
                        .contentType("application/json")
                        .header("Authorization", BEARER_TOKEN)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse();

        // THEN
        ProjectDto projectDto = objectMapper.readValue(response.getContentAsString(), ProjectDto.class);
        jpaProjectRepository.findById(projectDto.id())
                .ifPresentOrElse(
                        project -> {
                            assertEquals("Operis", project.getName());
                            assertEquals("Project management platform", project.getDescription());
                        },
                        () -> Assertions.fail("Project not found")
                );
    }

    @Test
    void createProjectShouldReturnBadRequestGivenEmptyName() throws Exception {
        // GIVEN
        CreateProjectPayload payload =
                new CreateProjectPayload("", "Project management platform");

        // WHEN
        mockMvc.perform(post("/api/projects")
                        .contentType("application/json")
                        .header("Authorization", BEARER_TOKEN)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createProjectShouldReturnInternalServerErrorGivenInvalidBearerToken() throws Exception {
        // GIVEN
        CreateProjectPayload payload =
                new CreateProjectPayload("Operis", "Project management platform");

        // WHEN
        MockHttpServletResponse response = mockMvc.perform(post("/api/projects")
                        .contentType("application/json")
                        .header("Authorization", "INVALID_BEARER_TOKEN")
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isInternalServerError())
                .andReturn().getResponse();

        // THEN
        ProjectApiError projectApiError = objectMapper.readValue(response.getContentAsString(), ProjectApiError.class);
        assertEquals("Error while parsing JWT token", projectApiError.message());
    }

    @Test
    void createProjectShouldReturnBadRequestWhenMissingHeaderBearerToken() throws Exception {
        // GIVEN
        CreateProjectPayload payload =
                new CreateProjectPayload("Operis", "Project management platform");

        // WHEN
        MockHttpServletResponse response = mockMvc.perform(post("/api/projects")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();

        // THEN
        ProjectApiError projectApiError = objectMapper.readValue(response.getContentAsString(), ProjectApiError.class);
        assertEquals("Required request header 'Authorization' for method parameter type String is not present", projectApiError.message());
    }
}