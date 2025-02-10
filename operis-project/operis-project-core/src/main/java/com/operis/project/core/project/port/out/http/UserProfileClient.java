package com.operis.project.core.project.port.out.http;

import com.operis.project.core.project.model.GetUserProfilesFromEmailsPayload;
import com.operis.project.core.project.model.Member;

import java.util.List;

public interface UserProfileClient {
    List<Member> find(GetUserProfilesFromEmailsPayload payload);
}
