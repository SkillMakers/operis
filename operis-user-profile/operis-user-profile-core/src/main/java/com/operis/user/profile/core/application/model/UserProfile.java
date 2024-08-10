package com.operis.user.profile.core.application.model;

import java.time.LocalDate;
import java.util.Objects;

public record UserProfile(
        Long id,
        String email,
        String firstName,
        String lastName,
        LocalDate birthDate
) {

    public UserProfile(String email, String firstName, String lastName, LocalDate birthDate) {
        this(null,
                Objects.requireNonNull(email, "Email cannot be null"),
                Objects.requireNonNull(firstName, "First name cannot be null"),
                Objects.requireNonNull(lastName, "Last name cannot be null"),
                birthDate);
    }
}
