package com.operis.user.account.mapper;

import com.operis.user.account.domain.UserAccount;
import com.operis.user.account.repository.UserAccountEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserAccountMapperTest {
    private final UserAccountMapper userAccountMapper = Mappers.getMapper(UserAccountMapper.class);

    @Test
    void testToUserAccountAccountDTO() {
        // Given
        UserAccountEntity userAccountEntity = new UserAccountEntity();
        userAccountEntity.setId(1L);
        userAccountEntity.setEmail("test@example.com");
        userAccountEntity.setPassword("password");
        userAccountEntity.setCreatedAt(LocalDateTime.now());
        userAccountEntity.setUpdatedAt(LocalDateTime.now());

        // When
        UserAccount userAccount = userAccountMapper.toUserAccountEntity(userAccountEntity);

        // Then
        assertNotNull(userAccount);
        assertEquals(userAccountEntity.getId(), userAccount.getId());
        assertEquals(userAccountEntity.getEmail(), userAccount.getEmail());
        assertNull(userAccount.getPassword());
        assertEquals(userAccountEntity.getCreatedAt(), userAccount.getCreatedAt());
        assertEquals(userAccountEntity.getUpdatedAt(), userAccount.getUpdatedAt());
    }

    @Test
    void testToUserAccount() {
        // Given
        UserAccount userAccount = new UserAccount();
        userAccount.setId(1L);
        userAccount.setEmail("test@example.com");
        userAccount.setPassword("password");
        userAccount.setCreatedAt(LocalDateTime.now());
        userAccount.setUpdatedAt(LocalDateTime.now());

        // When
        UserAccountEntity userAccountEntity = userAccountMapper.toUserAccountEntity(userAccount);

        // Then
        assertNotNull(userAccountEntity);
        assertEquals(userAccount.getId(), userAccountEntity.getId());
        assertEquals(userAccount.getEmail(), userAccountEntity.getEmail());
        assertEquals(userAccount.getPassword(), userAccountEntity.getPassword());
        assertEquals(userAccount.getCreatedAt(), userAccountEntity.getCreatedAt());
        assertEquals(userAccount.getUpdatedAt(), userAccountEntity.getUpdatedAt());
    }
}
