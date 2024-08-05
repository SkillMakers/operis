package com.operis.user.profile.core.domain.model;

import java.time.LocalDate;

public record UserProfile (String email, String firstName, String lastName, LocalDate birthDate) {

}
