package com.operis.service;

import com.operis.dto.UserAccountDTO;
import com.operis.model.User;
import com.operis.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class UserAccountServiceIntegrationTest {

    @Autowired
    private UserAccountService userAccountService;

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
        UserAccountDTO userAccountDTO = new UserAccountDTO();
        userAccountDTO.setEmail("test@example.com");
        userAccountDTO.setPassword("password");

        // When
        UserAccountDTO createdUserAccountDTO = userAccountService.createUser(userAccountDTO);

        // Then
        assertThat(createdUserAccountDTO).isNotNull();
        assertThat(createdUserAccountDTO.getId()).isNotNull();

        // Check if user is saved in repository
        User savedUser = userRepository.findById(createdUserAccountDTO.getId()).orElse(null);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo(userAccountDTO.getEmail());
        assertThat(passwordEncoder.matches(userAccountDTO.getPassword(), savedUser.getPassword())).isTrue();
    }
}
