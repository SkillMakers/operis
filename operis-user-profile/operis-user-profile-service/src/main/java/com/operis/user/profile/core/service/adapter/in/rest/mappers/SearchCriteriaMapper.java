package com.operis.user.profile.core.service.adapter.in.rest.mappers;

import com.operis.user.profile.core.application.model.SearchCriteria;
import org.springframework.stereotype.Component;

@Component
public final class SearchCriteriaMapper {
    public SearchCriteria from(String query) {
        return new SearchCriteria(query);
    }
}
