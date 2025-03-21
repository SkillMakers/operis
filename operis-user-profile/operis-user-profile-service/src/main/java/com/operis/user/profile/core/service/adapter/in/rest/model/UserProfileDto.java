package com.operis.user.profile.core.service.adapter.in.rest.model;

import java.time.LocalDate;

public record UserProfileDto(Long id, String email, String firstName, String lastName, LocalDate birthDate) {

}
