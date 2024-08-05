package com.operis.user.profile.core.service.adapters.outbound.persistence;

import com.operis.user.profile.core.application.port.outbound.UserProfilePersistencePort;
import com.operis.user.profile.core.domain.model.SearchCriteria;
import com.operis.user.profile.core.domain.model.UserProfile;
import com.operis.user.profile.core.service.adapters.outbound.persistence.mappers.UserProfileEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JPAUserProfileRepository implements UserProfilePersistencePort {
    private final JPAUserProfileSpringDataRepository jpaUserProfileSpringDataRepository;
    private final UserProfileEntityMapper userProfileEntityMapper;

    @Override
    public UserProfile save(UserProfile userProfile) {
        // FIXME add entities
        UserProfileEntity userProfileEntity = userProfileEntityMapper.toEntity(userProfile);

        return userProfileEntityMapper.toDomain(
                jpaUserProfileSpringDataRepository.save(userProfileEntity)
        );
    }

    @Override
    public List<UserProfile> search(SearchCriteria criteria) {
        return null;
    }
}
