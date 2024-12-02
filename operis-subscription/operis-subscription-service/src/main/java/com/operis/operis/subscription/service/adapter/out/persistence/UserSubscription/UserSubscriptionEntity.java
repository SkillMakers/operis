package com.operis.operis.subscription.service.adapter.out.persistence.UserSubscription;

import com.operis.operis.subscription.service.adapter.out.persistence.Subscription.SubscriptionEntity;
import com.operis.subscription.code.model.UserSubscription;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_subscription")
public class UserSubscriptionEntity {

    @Id
    @Column(name = "user_email", nullable = false, unique = true)
    private String userEmail;

    @ManyToOne
    @JoinColumn(name = "subscription_id", nullable = false)
    private SubscriptionEntity subscription;

    public static UserSubscriptionEntity from(UserSubscription userSubscription) {
        return new UserSubscriptionEntity(
                userSubscription.userEMail(),
                SubscriptionEntity.from(userSubscription.subscription())
        );
    }

    public UserSubscription toDomain() {
        return new UserSubscription(userEmail, subscription.toDomain());
    }
}
