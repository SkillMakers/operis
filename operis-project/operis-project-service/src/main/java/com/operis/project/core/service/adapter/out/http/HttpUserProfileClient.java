package com.operis.project.core.service.adapter.out.http;

import com.operis.project.core.application.project.model.GetUserProfilesFromEmailsPayload;
import com.operis.project.core.application.project.model.Member;
import com.operis.project.core.application.project.port.out.http.UserProfileClient;
import com.operis.project.core.service.adapter.out.http.UserProfileFeignClient.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HttpUserProfileClient implements UserProfileClient {

    private final UserProfileFeignClient userProfileFeignClient;
    
    @Override
    public List<Member> find(GetUserProfilesFromEmailsPayload payload) {
        return UserProfileResponse.toDomain(userProfileFeignClient.find(payload));
    }
}
