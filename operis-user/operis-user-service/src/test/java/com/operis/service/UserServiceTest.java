package com.operis.service;

import com.operis.dto.UserDTO;
import com.operis.model.User;
import com.operis.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testCreateUser() {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password");

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        UserDTO createdUserDTO = userService.createUser(userDTO);

        // Then
        assertNotNull(createdUserDTO);
        assertEquals(userDTO.getEmail(), createdUserDTO.getEmail());
        assertEquals(userDTO.getPassword(), createdUserDTO.getPassword());

        // Vérifie que le userRepository.save() a été appelé avec un argument de type User
        verify(userRepository, times(1)).save(any(User.class));
    }
}
