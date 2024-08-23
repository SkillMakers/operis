package com.operis.service;

import com.operis.dto.UserAccountDTO;
import com.operis.model.UserAccount;
import com.operis.repository.UserAccountRepository;
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
    private UserAccountRepository userAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        userAccountRepository.deleteAll();
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
        UserAccount savedUser = userAccountRepository.findById(createdUserAccountDTO.getId()).orElse(null);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo(userAccountDTO.getEmail());
        assertThat(passwordEncoder.matches(userAccountDTO.getPassword(), savedUser.getPassword())).isTrue();
    }
}
