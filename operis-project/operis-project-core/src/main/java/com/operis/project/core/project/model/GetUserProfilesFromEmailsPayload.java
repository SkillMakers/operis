package com.operis.project.core.project.model;

import java.util.List;

public record GetUserProfilesFromEmailsPayload(List<String> userProfilesEmails) {
}
