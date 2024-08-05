package com.operis.user.profile.core.service.adapters.outbound.persistence.mappers;

import com.operis.user.profile.core.domain.model.UserProfile;
import com.operis.user.profile.core.service.adapters.outbound.persistence.UserProfileEntity;
import org.springframework.stereotype.Component;

@Component
public final class UserProfileEntityMapper {

    public UserProfileEntity toEntity(UserProfile userProfile) {
        return new UserProfileEntity(
                null, // FIXME how to generate id ?
                userProfile.email(),
                userProfile.firstName(),
                userProfile.lastName(),
                userProfile.birthDate()
        );
    }

    public UserProfile toDomain(UserProfileEntity entity) {
        return new UserProfile(
                entity.getEmail(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getBirthDate()
        );
    }
}
