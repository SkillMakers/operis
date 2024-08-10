package com.operis.user.profile.core.service.adapter.in.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.operis.user.profile.core.application.model.UserProfile;
import com.operis.user.profile.core.application.port.in.UserProfileUseCases;
import com.operis.user.profile.core.service.adapter.in.rest.mappers.SearchCriteriaMapper;
import com.operis.user.profile.core.service.adapter.in.rest.mappers.UserProfileMapper;
import com.operis.user.profile.core.service.adapter.in.rest.model.CreateUserProfilePayload;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static java.time.LocalDate.of;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserProfileController.class)
class UserProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserProfileUseCases userProfileUseCases;

    @SpyBean
    private UserProfileMapper userProfileMapper;

    @SpyBean
    private SearchCriteriaMapper searchCriteriaMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    class Create {
        @Test
        void shouldCreateAnUserAccount() throws Exception {
            // Given
            CreateUserProfilePayload createUserProfile = new CreateUserProfilePayload(
                    "imad.test@gmail.com", "Imad", "Bou Akl", of(2024, 1, 1)

            );

            when(userProfileUseCases.save(any(UserProfile.class)))
                    .thenReturn(new UserProfile(1L, createUserProfile.email(),
                            createUserProfile.firstName(), createUserProfile.lastName(), createUserProfile.birthDate()));

            // When
            mockMvc.perform(post("/api/user-profiles")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createUserProfile)))
                    // Then
                    .andExpect(status().isCreated());
        }
    }

}