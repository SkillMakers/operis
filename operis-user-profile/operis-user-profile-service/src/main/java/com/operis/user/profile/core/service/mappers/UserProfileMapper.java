package com.operis.user.profile.core.service.mappers;

import com.operis.user.profile.core.domain.model.UserProfile;
import com.operis.user.profile.core.service.dto.UserProfileDto;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
public final class UserProfileMapper {
    public UserProfile toDomain(UserProfileDto dto) {
        return new UserProfile(dto.email(), dto.firstName(), dto.lastName(), dto.birthDate());
    }

    public UserProfileDto toDto(UserProfile domain) {
        return new UserProfileDto(domain.email(), domain.firstName(), domain.lastName(), domain.birthDate());
    }

    public List<UserProfileDto> toDto(List<UserProfile> domains) {
        return CollectionUtils.isEmpty(domains) ? List.of() : domains.stream()
                .map(this::toDto)
                .toList();
    }
}
