package com.operis.user.profile.core.service.adapters.inbound.rest;

import com.operis.user.profile.core.application.port.inbound.UserProfileUseCases;
import com.operis.user.profile.core.service.dto.UserProfileDto;
import com.operis.user.profile.core.service.mappers.SearchCriteriaMapper;
import com.operis.user.profile.core.service.mappers.UserProfileMapper;
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

    @PostMapping("/")
    public UserProfileDto create(UserProfileDto userProfileDto) {
        return userProfileMapper.toDto(
                userProfileUseCases.save(userProfileMapper.toDomain(userProfileDto))
        );
    }

    @PutMapping("/")
    public UserProfileDto update(UserProfileDto userProfileDto) {
        return userProfileMapper.toDto(
                userProfileUseCases.save(userProfileMapper.toDomain(userProfileDto))
        );
    }

    @GetMapping("/search")
    public List<UserProfileDto> search(@RequestParam String searchValue) {
        return userProfileMapper.toDto(
                userProfileUseCases.search(searchCriteriaMapper.from(searchValue))
        );
    }
}
