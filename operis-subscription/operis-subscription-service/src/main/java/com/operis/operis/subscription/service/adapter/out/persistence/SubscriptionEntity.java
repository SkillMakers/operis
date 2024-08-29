package com.operis.operis.subscription.service.adapter.out.persistence;

import com.operis.subscription.code.model.Subscription;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    
    public static SubscriptionEntity from(Subscription subscription) {
        return new SubscriptionEntity(
                subscription.id(),
                subscription.name(),
                subscription.description()
        );
    }

}
