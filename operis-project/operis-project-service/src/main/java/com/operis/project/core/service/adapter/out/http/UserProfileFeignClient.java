package com.operis.project.core.service.adapter.out.http;

import com.operis.project.core.application.project.model.GetUserProfilesFromEmailsPayload;
import com.operis.project.core.application.project.model.Member;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "operis-user-profile-service")
@LoadBalancerClient(name = "operis-user-profile-service")
public interface UserProfileFeignClient {

    @PostMapping("/api/user-profiles/find")
    List<UserProfileResponse> find(@RequestBody GetUserProfilesFromEmailsPayload payload);

    record UserProfileResponse(String email, String firstName, String lastName) {
        public Member toDomain() {
            return new Member(email, firstName, lastName);
        }

        public static List<Member> toDomain(List<UserProfileResponse> userProfiles) {
            return userProfiles.stream()
                    .map(UserProfileResponse::toDomain)
                    .toList();
        }
    }

    record UserProfileApiError(Integer statusCode,
                               String httpStatus,
                               String message,
                               List<String> details) {

    }
}
