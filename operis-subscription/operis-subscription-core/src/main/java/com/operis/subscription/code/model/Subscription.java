package com.operis.subscription.code.model;

import java.util.List;
import java.util.Objects;

public record Subscription(
        Long id,
        String name,
        String description,
        List<Features> features
) {

    public Subscription(String name, String description,List<Features> features) {
        this(null,
                Objects.requireNonNull(name, "Name cannot be null"),
                Objects.requireNonNull(description, "Description name cannot be null"),
                Objects.requireNonNull(features, "The list of features cannot be null"));
    }
}
