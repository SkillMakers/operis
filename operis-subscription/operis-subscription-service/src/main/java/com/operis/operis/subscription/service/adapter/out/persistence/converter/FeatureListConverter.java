package com.operis.operis.subscription.service.adapter.out.persistence.converter;

import com.operis.subscription.code.model.Feature;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter(autoApply = true)
public class FeatureListConverter implements AttributeConverter<List<Feature>, String> {

    private static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(List<Feature> features) {
        if (features == null || features.isEmpty()) {
            return "";
        }
        return features.stream()
                .map(Enum::name)
                .collect(Collectors.joining(SPLIT_CHAR));
    }

    @Override
    public List<Feature> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return List.of();
        }
        return Arrays.stream(dbData.split(SPLIT_CHAR))
                .map(Feature::valueOf)
                .collect(Collectors.toList());
    }
}
