package com.operis.subscription.code.model;

import com.operis.subscription.code.model.utils.ValidatorUtils;

import java.util.List;
import java.util.Objects;

public record Subscription(
        String id,
        String name,
        String description,
        List<Features> features
) {

    public Subscription(String id, String name, String description, List<Features> features) {
        this.id = ValidatorUtils.validateNonEmpty(id, "ID cannot be null");
        this.name = ValidatorUtils.validateNonEmpty(name, "Name cannot be null");
        this.description = ValidatorUtils.validateNonEmpty(description, "Description cannot be null");
        this.features = Objects.requireNonNull(features, "The list of features cannot be null");
    }

}
