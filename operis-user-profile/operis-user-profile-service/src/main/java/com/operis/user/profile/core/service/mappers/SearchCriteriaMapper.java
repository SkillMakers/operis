package com.operis.user.profile.core.service.mappers;

import com.operis.user.profile.core.domain.model.SearchCriteria;
import org.springframework.stereotype.Component;

@Component
public final class SearchCriteriaMapper {
    public SearchCriteria from(String searchValue) {
        return new SearchCriteria(searchValue);
    }
}
