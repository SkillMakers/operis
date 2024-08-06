package com.operis.user.profile.core.application.service;

import com.operis.user.profile.core.application.model.SearchCriteria;
import com.operis.user.profile.core.application.model.UserProfile;
import com.operis.user.profile.core.application.port.in.UserProfileUseCases;
import com.operis.user.profile.core.application.port.out.persistence.UserProfileRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserProfileService implements UserProfileUseCases {

    private final UserProfileRepository userProfileRepository;

    @Override
    public UserProfile save(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

    @Override
    public List<UserProfile> search(SearchCriteria searchCriteria) {
        return userProfileRepository.search(searchCriteria);
    }
}
