package com.operis.project.core.application.project.port.out.http;

import com.operis.project.core.application.project.model.GetUserSubscriptionPayload;
import com.operis.project.core.application.project.model.UserSubscription;

public interface UserSubscriptionClient {
    UserSubscription get(GetUserSubscriptionPayload payload);
}
