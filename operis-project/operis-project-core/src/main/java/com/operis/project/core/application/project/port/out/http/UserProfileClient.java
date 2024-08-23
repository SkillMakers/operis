package com.operis.project.core.application.project.port.out.http;

import com.operis.project.core.application.project.model.GetUserProfilesFromEmailsPayload;
import com.operis.project.core.application.project.model.Member;

import java.util.List;

public interface UserProfileClient {
    List<Member> find(GetUserProfilesFromEmailsPayload payload);
}
