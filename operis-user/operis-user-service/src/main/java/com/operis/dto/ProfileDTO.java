package com.operis.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ProfileDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Long userId;
}
