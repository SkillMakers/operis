package com.operis.user.profile.core.application.port.in;

import com.operis.user.profile.core.application.model.SearchCriteria;
import com.operis.user.profile.core.application.model.UserProfile;

import java.util.List;

public interface UserProfileUseCases {
    UserProfile save(UserProfile userProfile);
    List<UserProfile> search(SearchCriteria searchCriteria);
}
