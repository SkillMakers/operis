package com.operis.user.profile.core.application.port.out.persistence;

import com.operis.user.profile.core.application.model.SearchCriteria;
import com.operis.user.profile.core.application.model.UserProfile;

import java.util.List;

public interface UserProfileRepository {
    UserProfile save(UserProfile userProfile);
    List<UserProfile> search(SearchCriteria criteria);
}
