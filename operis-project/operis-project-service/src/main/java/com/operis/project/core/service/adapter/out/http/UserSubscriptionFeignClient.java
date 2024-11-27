package com.operis.project.core.service.adapter.out.http;

import com.operis.project.core.application.project.model.GetUserSubscriptionPayload;
import com.operis.project.core.application.project.model.UserSubscription;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "operis-subscription-service")
@LoadBalancerClient(name = "operis-subscription-service")
public interface UserSubscriptionFeignClient {

    @PostMapping("/api/user-subscriptions/get")
    UserSubscriptionResponse get(@RequestBody GetUserSubscriptionPayload payload);

    record UserSubscriptionResponse(String userEmail, SubscriptionResponse subscription) {
        public static UserSubscription toDomain(UserSubscriptionResponse userSubscription) {
            return userSubscription != null ? new UserSubscription(userSubscription.userEmail(),
                    userSubscription.subscription().name(),
                    userSubscription.subscription().features().stream()
                            .map(FeatureResponse::toDomain)
                            .toList())
                    : null;
        }
    }

    record SubscriptionResponse(String id, String name, String description, List<FeatureResponse> features) {
    }

    enum FeatureResponse {
        TASK_EXPORT;

        UserSubscription.Feature toDomain() {
            return switch (this) {
                case TASK_EXPORT -> UserSubscription.Feature.EXPORT;
            };
        }
    }
}


