package com.operis.operis.subscription.service.adapter.in.rest.model;

import com.operis.subscription.code.model.Feature;
import com.operis.subscription.code.model.model.CreateSubscriptionCommand;

import java.util.List;

public record CreateSubscriptionPayloadRecord(String name, String description, List<Feature> features) {

    public CreateSubscriptionCommand toCommand() {
        return new CreateSubscriptionCommand(
                name(),
                description(),
                features
        );
    }

}
