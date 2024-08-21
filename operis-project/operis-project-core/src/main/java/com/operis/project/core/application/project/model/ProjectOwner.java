package com.operis.project.core.application.project.model;

public record ProjectOwner(String userEmail) {

    public ProjectOwner {
        if (isUserEmailNullOrBlank(userEmail)) {
            throw new IllegalArgumentException("userEmail must not be null or blank");
        }
    }

    static boolean isUserEmailNullOrBlank(String userEmail) {
        return userEmail == null || userEmail.isBlank();
    }
}
