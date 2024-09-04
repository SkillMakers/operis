package com.operis.operis.subscription.service.adapter.out.persistence;

import com.operis.operis.subscription.service.adapter.out.persistence.converter.FeatureListConverter;
import com.operis.subscription.code.model.Feature;
import com.operis.subscription.code.model.Subscription;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subscription")
public class SubscriptionEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Convert(converter = FeatureListConverter.class)
    @Column(nullable = false)
    private List<Feature> features;

    public static SubscriptionEntity from(Subscription subscription) {
        return new SubscriptionEntity(
                subscription.id(),
                subscription.name(),
                subscription.description(),
                subscription.features()
        );
    }

}
