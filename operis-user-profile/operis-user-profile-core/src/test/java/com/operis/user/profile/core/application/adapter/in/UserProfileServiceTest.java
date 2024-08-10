package com.operis.user.profile.core.application.adapter.in;

import com.operis.user.profile.core.application.model.SearchCriteria;
import com.operis.user.profile.core.application.model.UserProfile;
import com.operis.user.profile.core.application.model.exception.NotFoundException;
import com.operis.user.profile.core.application.port.out.persistence.UserProfileRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceTest {

    private static final UserProfile IMAD_PROFILE_WITH_ID = new UserProfile(
            100000L,
            "imad.test@gmail.com",
            "Imad",
            "Bou Akl",
            LocalDate.of(1983, 1, 1)
    );
    private static final UserProfile RONALD_PROFILE_WITH_ID = new UserProfile(
            100001L,
            "ronald.test@gmail.com",
            "Ronald",
            "Polanco",
            LocalDate.of(1980, 1, 1)
    );

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private UserProfileService userProfileUseCases;

    @Nested
    class CreateWithSave {
        @Test
        void shouldCreateANewUserProfileWhenIdIsNull() {
            // Given
            UserProfile userProfile = new UserProfile(
                    null,
                    "imad.test@gmail.com",
                    "Imad",
                    "Bou Akl",
                    LocalDate.of(1990, 1, 1)
            );
            when(userProfileRepository.save(userProfile)).thenReturn(copy(userProfile, 200L));

            // When
            UserProfile createdUser = userProfileUseCases.save(userProfile);

            // Then
            verify(userProfileRepository).save(eq(userProfile));

            // And
            assertThat(createdUser.id()).isEqualTo(200L);
            assertThat(createdUser.firstName()).isEqualTo(userProfile.firstName());
            assertThat(createdUser.lastName()).isEqualTo(userProfile.lastName());
            assertThat(createdUser.email()).isEqualTo(userProfile.email());
            assertThat(createdUser.birthDate()).isEqualTo(userProfile.birthDate());
        }
    }

    @Nested
    class UpdateWithSave {
        @Test
        void shouldUpdateAnExistingUserProfileWhenIdIsNotNull() {
            // Given
            UserProfile userProfile = IMAD_PROFILE_WITH_ID;
            when(userProfileRepository.findById(userProfile.id())).thenReturn(Optional.of(userProfile));

            // When
            userProfileUseCases.save(userProfile);

            // Then
            verify(userProfileRepository).save(eq(userProfile));
        }

        @Test
        void shouldThrowNotFoundExceptionWhenTryingToUpdateAUserProfileAndItDoesntExist() {
            // Given
            UserProfile userProfile = IMAD_PROFILE_WITH_ID;
            when(userProfileRepository.findById(userProfile.id())).thenReturn(Optional.empty());

            // When / Then
            assertThrows(NotFoundException.class, () -> userProfileUseCases.save(userProfile));

            // And
            verify(userProfileRepository, times(0)).save(eq(userProfile));
        }
    }

    @Nested
    class Search {
        @Test
        void shouldReturnEmptyListWhenNoUserProfileMatchesTheSearchCriteria() {
            // When
            List<UserProfile> userProfiles = userProfileUseCases.search(new SearchCriteria("O"));

            // Then
            assertThat(userProfiles).isEmpty();
        }

        @Test
        void shouldReturnUserProfilesMatchingTheGivenSearchCriteria() {
            // Given
            when(userProfileRepository.search(new SearchCriteria("O")))
                    .thenReturn(List.of(IMAD_PROFILE_WITH_ID, RONALD_PROFILE_WITH_ID));

            // When
            List<UserProfile> userProfiles = userProfileUseCases.search(new SearchCriteria("O"));

            // Then
            assertThat(userProfiles).containsExactlyInAnyOrder(IMAD_PROFILE_WITH_ID, RONALD_PROFILE_WITH_ID);
        }
    }

    private static UserProfile copy(UserProfile userProfile, Long id) {
        return new UserProfile(
                id,
                userProfile.email(),
                userProfile.firstName(),
                userProfile.lastName(),
                userProfile.birthDate()
        );
    }
}