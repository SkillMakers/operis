package com.operis.mapper;

import com.operis.dto.UserAccountDTO;
import com.operis.model.UserAccount;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserAccountMapperTest {

    private final UserAccountMapper userAccountMapper = Mappers.getMapper(UserAccountMapper.class);

    @Test
    void testToUserAccountAccountDTO() {
        // Given
        UserAccount userAccount = new UserAccount();
        userAccount.setId(1L);
        userAccount.setEmail("test@example.com");
        userAccount.setPassword("password");
        userAccount.setCreatedAt(LocalDateTime.now());
        userAccount.setUpdatedAt(LocalDateTime.now());

        // When
        UserAccountDTO userAccountDTO = userAccountMapper.toUserAccountDTO(userAccount);

        // Then
        assertNotNull(userAccountDTO);
        assertEquals(userAccount.getId(), userAccountDTO.getId());
        assertEquals(userAccount.getEmail(), userAccountDTO.getEmail());
        assertNull(userAccountDTO.getPassword());
        assertEquals(userAccount.getCreatedAt(), userAccountDTO.getCreatedAt());
        assertEquals(userAccount.getUpdatedAt(), userAccountDTO.getUpdatedAt());
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
        UserAccount userAccount = userAccountMapper.toUserAccount(userAccountDTO);

        // Then
        assertNotNull(userAccount);
        assertEquals(userAccountDTO.getId(), userAccount.getId());
        assertEquals(userAccountDTO.getEmail(), userAccount.getEmail());
        assertEquals(userAccountDTO.getPassword(), userAccount.getPassword());
        assertEquals(userAccountDTO.getCreatedAt(), userAccount.getCreatedAt());
        assertEquals(userAccountDTO.getUpdatedAt(), userAccount.getUpdatedAt());
    }
}
