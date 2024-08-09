package com.operis.service;

import com.operis.dto.UserDTO;
import com.operis.model.User;
import com.operis.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
    }

    @Test
    public void testCreateUser() {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password");

        // When
        UserDTO createdUserDTO = userService.createUser(userDTO);

        // Then
        assertThat(createdUserDTO).isNotNull();
        assertThat(createdUserDTO.getId()).isNotNull();

        // Check if user is saved in repository
        User savedUser = userRepository.findById(createdUserDTO.getId()).orElse(null);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo(userDTO.getEmail());
        assertThat(passwordEncoder.matches(userDTO.getPassword(), savedUser.getPassword())).isTrue();
    }
}
