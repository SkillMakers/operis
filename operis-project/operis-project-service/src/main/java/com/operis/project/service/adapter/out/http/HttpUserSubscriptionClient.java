package com.operis.project.service.adapter.out.http;

import com.operis.project.core.project.model.GetUserSubscriptionPayload;
import com.operis.project.core.project.model.UserSubscription;
import com.operis.project.core.project.port.out.http.UserSubscriptionClient;
import com.operis.project.service.adapter.out.http.UserSubscriptionFeignClient.UserSubscriptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HttpUserSubscriptionClient implements UserSubscriptionClient {

    private final UserSubscriptionFeignClient userSubscriptionFeignClient;

    @Override
    public UserSubscription get(GetUserSubscriptionPayload payload) {
        return UserSubscriptionResponse.toDomain(userSubscriptionFeignClient.get(payload));
    }
}
