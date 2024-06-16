package com.operis.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private UserDTO user;
}
