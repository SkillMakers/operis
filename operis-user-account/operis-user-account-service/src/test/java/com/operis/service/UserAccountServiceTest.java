package com.operis.service;

import com.operis.dto.UserAccountDTO;
import com.operis.model.UserAccount;
import com.operis.repository.UserAccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class UserAccountServiceTest {

    @Autowired
    private UserAccountService userAccountService;

    @MockBean
    private UserAccountRepository userRepository;

    @Test
    public void testCreateUser() {
        // Given
        UserAccountDTO userAccountDTO = new UserAccountDTO();
        userAccountDTO.setEmail("test@example.com");
        userAccountDTO.setPassword("password");

        UserAccount user = new UserAccount();
        user.setEmail(userAccountDTO.getEmail());
        user.setPassword(userAccountDTO.getPassword());

        when(userRepository.save(any(UserAccount.class))).thenReturn(user);

        // When
        UserAccountDTO createdUserAccountDTO = userAccountService.createUser(userAccountDTO);

        // Then
        assertNotNull(createdUserAccountDTO);
        assertEquals(userAccountDTO.getEmail(), createdUserAccountDTO.getEmail());
        assertNull(createdUserAccountDTO.getPassword());

        // Vérifie que le userRepository.save() a été appelé avec un argument de type User
        verify(userRepository, times(1)).save(any(UserAccount.class));
    }
}
