package com.operis.user.profile.core.service.adapter.out.persistence.mappers;

import com.operis.user.profile.core.application.model.UserProfile;
import com.operis.user.profile.core.service.adapter.out.persistence.UserProfileEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
public final class UserProfileEntityMapper {

    public UserProfileEntity toEntity(UserProfile userProfile) {
        return new UserProfileEntity(
                userProfile.id(),
                userProfile.email(),
                userProfile.firstName(),
                userProfile.lastName(),
                userProfile.birthDate()
        );
    }

    public List<UserProfile> toDomain(List<UserProfileEntity> entities) {
        return CollectionUtils.isEmpty(entities)
                ? List.of() :
                entities.stream().map(this::toDomain)
                        .toList();
    }

    public UserProfile toDomain(UserProfileEntity entity) {
        return new UserProfile(
                entity.getId(),
                entity.getEmail(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getBirthDate()
        );
    }
}
