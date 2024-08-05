package com.operis.user.profile.core.service.dto;

import java.time.LocalDate;

public record UserProfileDto(String email, String firstName, String lastName, LocalDate birthDate) {

}
