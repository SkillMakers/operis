package com.operis.mapper;

import com.operis.dto.UserAccountDTO;
import com.operis.model.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserAccountMapperTest {

    private final UserAccountMapper userAccountMapper = Mappers.getMapper(UserAccountMapper.class);

    @Test
    void testToUserAccountAccountDTO() {
        // Given
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        // When
        UserAccountDTO userAccountDTO = userAccountMapper.toUserAccountDTO(user);

        // Then
        assertNotNull(userAccountDTO);
        assertEquals(user.getId(), userAccountDTO.getId());
        assertEquals(user.getEmail(), userAccountDTO.getEmail());
        assertNull(userAccountDTO.getPassword());
        assertEquals(user.getCreatedAt(), userAccountDTO.getCreatedAt());
        assertEquals(user.getUpdatedAt(), userAccountDTO.getUpdatedAt());
    }

    @Test
    void testToUserAccount() {
        // Given
        UserAccountDTO userAccountDTO = new UserAccountDTO();
        userAccountDTO.setId(1L);
        userAccountDTO.setEmail("test@example.com");
        userAccountDTO.setPassword("password");
        userAccountDTO.setCreatedAt(LocalDateTime.now());
        userAccountDTO.setUpdatedAt(LocalDateTime.now());

        // When
        User user = userAccountMapper.toUserAccount(userAccountDTO);

        // Then
        assertNotNull(user);
        assertEquals(userAccountDTO.getId(), user.getId());
        assertEquals(userAccountDTO.getEmail(), user.getEmail());
        assertEquals(userAccountDTO.getPassword(), user.getPassword());
        assertEquals(userAccountDTO.getCreatedAt(), user.getCreatedAt());
        assertEquals(userAccountDTO.getUpdatedAt(), user.getUpdatedAt());
    }
}
