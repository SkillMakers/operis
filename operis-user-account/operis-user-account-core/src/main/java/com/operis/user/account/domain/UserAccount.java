package com.operis.user.account.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserAccount {
    private Long id;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
