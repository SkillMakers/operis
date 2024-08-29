package com.operis.subscription.code.model.model;

import com.operis.subscription.code.model.Feature;

import java.util.List;

public record CreateSubscriptionCommand(String name, String description, List<Feature> features) {

    public CreateSubscriptionCommand {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be null or blank");
        }

        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("description must not be null");
        }

        if (features == null || features.isEmpty()) {
            throw new IllegalArgumentException("features must not be null or empty");
        }
    }
}
