package com.operis.user.profile.core.application.port.inbound;

import com.operis.user.profile.core.domain.model.SearchCriteria;
import com.operis.user.profile.core.domain.model.UserProfile;

import java.util.List;

public interface UserProfileUseCases {
    UserProfile save(UserProfile userProfile);
    List<UserProfile> search(SearchCriteria searchCriteria);
}
