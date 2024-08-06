package com.operis.user.profile.core.application.model;

import java.time.LocalDate;

public record UserProfile (
        Long id,
        String email,
        String firstName,
        String lastName,
        LocalDate birthDate
) {

    public UserProfile(String email, String firstName, String lastName, LocalDate birthDate) {
        this(null, email, firstName, lastName, birthDate);
    }
}
