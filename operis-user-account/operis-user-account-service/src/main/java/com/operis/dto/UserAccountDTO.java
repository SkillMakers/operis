package com.operis.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAccountDTO {
    private Long id;
    @Email(message = "L'adresse email ne respecte pas le bon format")
    private String email;
    @Size(min = 6, message = "Le mot de passe doit avoir au moins 6 caractères")
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
