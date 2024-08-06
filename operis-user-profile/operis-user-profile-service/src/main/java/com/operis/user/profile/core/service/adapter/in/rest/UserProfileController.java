package com.operis.user.profile.core.service.adapter.in.rest;

import com.operis.user.profile.core.application.port.in.UserProfileUseCases;
import com.operis.user.profile.core.service.adapter.in.rest.mappers.SearchCriteriaMapper;
import com.operis.user.profile.core.service.adapter.in.rest.mappers.UserProfileMapper;
import com.operis.user.profile.core.service.adapter.in.rest.model.CreateUserProfilePayload;
import com.operis.user.profile.core.service.adapter.in.rest.model.UpdateUserProfilePayload;
import com.operis.user.profile.core.service.adapter.in.rest.model.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileUseCases userProfileUseCases;
    private final SearchCriteriaMapper searchCriteriaMapper;
    private final UserProfileMapper userProfileMapper;

    @PostMapping("")
    public UserProfileDto create(@RequestBody CreateUserProfilePayload payload) {
        return userProfileMapper.toDto(
                userProfileUseCases.save(userProfileMapper.toDomain(payload))
        );
    }

    @PutMapping("")
    public UserProfileDto update(@RequestBody UpdateUserProfilePayload payload) {
        return userProfileMapper.toDto(
                userProfileUseCases.save(userProfileMapper.toDomain(payload))
        );
    }

    @GetMapping("/search")
    public List<UserProfileDto> search(@RequestParam String query) {
        return userProfileMapper.toDto(
                userProfileUseCases.search(searchCriteriaMapper.from(query))
        );
    }
}
