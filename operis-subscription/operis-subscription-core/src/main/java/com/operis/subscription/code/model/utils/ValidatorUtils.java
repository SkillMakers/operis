package com.operis.subscription.code.model.utils;

public class ValidatorUtils {
    public static String validateNonEmpty(String value, String errorMessage) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(errorMessage);
        }
        return value;
    }
}
