package com.operis.user.profile.core.service.adapter.in.rest.mappers;

import com.operis.user.profile.core.application.model.UserProfile;
import com.operis.user.profile.core.service.adapter.in.rest.model.CreateUserProfilePayload;
import com.operis.user.profile.core.service.adapter.in.rest.model.UpdateUserProfilePayload;
import com.operis.user.profile.core.service.adapter.in.rest.model.UserProfileDto;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
public final class UserProfileMapper {
    public UserProfile toDomain(CreateUserProfilePayload dto) {
        return new UserProfile(dto.email(),
                dto.firstName(), dto.lastName(), dto.birthDate());
    }

    public UserProfile toDomain(UpdateUserProfilePayload dto) {
        return new UserProfile(dto.id(), dto.email(),
                dto.firstName(), dto.lastName(), dto.birthDate());
    }

    public UserProfileDto toDto(UserProfile domain) {
        return new UserProfileDto(
                domain.id(),
                domain.email(),
                domain.firstName(),
                domain.lastName(),
                domain.birthDate()
        );
    }

    public List<UserProfileDto> toDto(List<UserProfile> domains) {
        return CollectionUtils.isEmpty(domains) ? List.of() : domains.stream()
                .map(this::toDto)
                .toList();
    }
}
