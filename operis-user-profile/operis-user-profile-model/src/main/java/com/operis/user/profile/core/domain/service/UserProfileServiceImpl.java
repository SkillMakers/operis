package com.operis.user.profile.core.domain.service;

import com.operis.user.profile.core.application.port.inbound.UserProfileUseCases;
import com.operis.user.profile.core.application.port.outbound.UserProfilePersistencePort;
import com.operis.user.profile.core.domain.model.SearchCriteria;
import com.operis.user.profile.core.domain.model.UserProfile;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileUseCases {

    private final UserProfilePersistencePort userProfilePersistencePort;

    @Override
    public UserProfile save(UserProfile userProfile) {
        return userProfilePersistencePort.save(userProfile);
    }

    @Override
    public List<UserProfile> search(SearchCriteria searchCriteria) {
        return userProfilePersistencePort.search(searchCriteria);
    }
}
