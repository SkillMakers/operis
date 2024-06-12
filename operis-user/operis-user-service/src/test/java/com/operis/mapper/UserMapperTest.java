package com.operis.mapper;

import com.operis.dto.UserDTO;
import com.operis.model.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void testToUserDTO() {
        // Given
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        // When
        UserDTO userDTO = userMapper.toUserDTO(user);

        // Then
        assertNotNull(userDTO);
        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertNull(userDTO.getPassword());
        assertEquals(user.getCreatedAt(), userDTO.getCreatedAt());
        assertEquals(user.getUpdatedAt(), userDTO.getUpdatedAt());
    }

    @Test
    void testToUser() {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password");
        userDTO.setCreatedAt(LocalDateTime.now());
        userDTO.setUpdatedAt(LocalDateTime.now());

        // When
        User user = userMapper.toUser(userDTO);

        // Then
        assertNotNull(user);
        assertEquals(userDTO.getId(), user.getId());
        assertEquals(userDTO.getEmail(), user.getEmail());
        assertEquals(userDTO.getPassword(), user.getPassword());
        assertEquals(userDTO.getCreatedAt(), user.getCreatedAt());
        assertEquals(userDTO.getUpdatedAt(), user.getUpdatedAt());
    }
}
