package com.operis.user.profile.core.application.port.outbound;

import com.operis.user.profile.core.domain.model.SearchCriteria;
import com.operis.user.profile.core.domain.model.UserProfile;

import java.util.List;

public interface UserProfilePersistencePort {
    UserProfile save(UserProfile userProfile);
    List<UserProfile> search(SearchCriteria criteria);
}
