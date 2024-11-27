package com.operis.project.core.application.project.model;

import org.springframework.util.CollectionUtils;

import java.util.List;

public record UserSubscription(String userEmail, String name, List<Feature> features) {

    public boolean isExportAllowed() {
        return isExportAllowed(this);
    }

    public static boolean isExportAllowed(UserSubscription userSubscription) {
        return userSubscription != null && !CollectionUtils.isEmpty(userSubscription.features) && userSubscription.features.contains(Feature.EXPORT);
    }

    public enum Feature {
        EXPORT
    }
}
