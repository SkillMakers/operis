package com.operis.user.profile.core.service.adapter.out.persistence;

import com.operis.user.profile.core.application.model.SearchCriteria;
import com.operis.user.profile.core.application.model.UserProfile;
import com.operis.user.profile.core.application.port.out.persistence.UserProfileRepository;
import com.operis.user.profile.core.service.adapter.out.persistence.mappers.UserProfileEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JPAUserProfileRepository implements UserProfileRepository {
    private final JPAUserProfileSpringDataRepository jpaUserProfileSpringDataRepository;
    private final UserProfileEntityMapper userProfileEntityMapper;

    @Override
    public UserProfile save(UserProfile userProfile) {
        UserProfileEntity userProfileEntity = userProfileEntityMapper.toEntity(userProfile);

        return userProfileEntityMapper.toDomain(
                jpaUserProfileSpringDataRepository.save(userProfileEntity)
        );
    }

    @Override
    public List<UserProfile> search(SearchCriteria criteria) {
        return userProfileEntityMapper.toDomain(
                jpaUserProfileSpringDataRepository.findByFirstNameOrLastName(criteria.query())
        );
    }

    @Override
    public Optional<UserProfile> findById(Long id) {
        return jpaUserProfileSpringDataRepository.findById(id)
                .map(userProfileEntityMapper::toDomain);
    }

    @Override
    public List<UserProfile> findByEmailIn(List<String> emails) {
        return userProfileEntityMapper.toDomain(
                jpaUserProfileSpringDataRepository.findByEmailIn(emails)
        );
    }
}
