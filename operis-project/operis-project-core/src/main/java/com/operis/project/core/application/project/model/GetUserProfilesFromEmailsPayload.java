package com.operis.project.core.application.project.model;

import java.util.List;

public record GetUserProfilesFromEmailsPayload(List<String> userProfilesEmails) {
}
