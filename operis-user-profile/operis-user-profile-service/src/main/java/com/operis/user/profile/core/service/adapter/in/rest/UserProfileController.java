package com.operis.user.profile.core.service.adapter.in.rest;

import com.operis.user.profile.core.application.port.in.UserProfileUseCases;
import com.operis.user.profile.core.service.adapter.in.rest.mappers.SearchCriteriaMapper;
import com.operis.user.profile.core.service.adapter.in.rest.mappers.UserProfileMapper;
import com.operis.user.profile.core.service.adapter.in.rest.model.CreateUserProfilePayload;
import com.operis.user.profile.core.service.adapter.in.rest.model.GetUserProfilesFromEmailsPayload;
import com.operis.user.profile.core.service.adapter.in.rest.model.UpdateUserProfilePayload;
import com.operis.user.profile.core.service.adapter.in.rest.model.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-profiles")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileUseCases userProfileUseCases;
    private final SearchCriteriaMapper searchCriteriaMapper;
    private final UserProfileMapper userProfileMapper;

    @PostMapping("")
    public ResponseEntity<UserProfileDto> create(@RequestBody CreateUserProfilePayload payload) {
        UserProfileDto userProfileDto = userProfileMapper.toDto(
                userProfileUseCases.save(userProfileMapper.toDomain(payload))
        );
        return new ResponseEntity<>(userProfileDto, HttpStatus.CREATED);
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

    @PostMapping("/find")
    public ResponseEntity<List<UserProfileDto>> getUserProfilesFromEmails(
            @RequestBody GetUserProfilesFromEmailsPayload payload) {
        List<UserProfileDto> userProfileDTO = userProfileMapper.toDto(
                userProfileUseCases.findByEmails(payload.userProfilesEmails())
        );

        return new ResponseEntity<>(userProfileDTO, HttpStatus.OK);
    }
}
