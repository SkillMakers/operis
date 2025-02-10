package com.operis.project.core.project.port.out.http;

import com.operis.project.core.project.model.GetUserSubscriptionPayload;
import com.operis.project.core.project.model.UserSubscription;

public interface UserSubscriptionClient {
    UserSubscription get(GetUserSubscriptionPayload payload);
}
