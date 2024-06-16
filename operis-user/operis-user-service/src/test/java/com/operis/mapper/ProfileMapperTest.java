package com.operis.mapper;

import com.operis.dto.ProfileDTO;
import com.operis.dto.UserDTO;
import com.operis.model.Profile;
import com.operis.model.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class ProfileMapperTest {

    @Autowired
    private ProfileMapper profileMapper;

    @Test
    public void testToDto() {
        // Given
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        Profile profile = new Profile();
        profile.setId(1L);
        profile.setFirstName("John");
        profile.setLastName("Doe");
        profile.setBirthDate(LocalDate.of(1990, 1, 1));
        profile.setUser(user);

        // When
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // Then
        assertNotNull(profileDTO);
        assertEquals(profile.getId(), profileDTO.getId());
        assertEquals(profile.getFirstName(), profileDTO.getFirstName());
        assertEquals(profile.getLastName(), profileDTO.getLastName());
        assertEquals(profile.getBirthDate(), profileDTO.getBirthDate());
        assertNotNull(profileDTO.getUser());
        assertEquals(user.getId(), profileDTO.getUser().getId());
        assertEquals(user.getEmail(), profileDTO.getUser().getEmail());
    }

    @Test
    public void testToEntity() {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password123");

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(1L);
        profileDTO.setFirstName("John");
        profileDTO.setLastName("Doe");
        profileDTO.setBirthDate(LocalDate.of(1990, 1, 1));
        profileDTO.setUser(userDTO);

        // When
        Profile profile = profileMapper.toEntity(profileDTO);

        // Then
        assertNotNull(profile);
        assertEquals(profileDTO.getId(), profile.getId());
        assertEquals(profileDTO.getFirstName(), profile.getFirstName());
        assertEquals(profileDTO.getLastName(), profile.getLastName());
        assertEquals(profileDTO.getBirthDate(), profile.getBirthDate());
        assertNotNull(profile.getUser());
        assertEquals(userDTO.getId(), profile.getUser().getId());
        assertEquals(userDTO.getEmail(), profile.getUser().getEmail());
    }
}
