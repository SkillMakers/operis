package com.operis.controller;

import com.operis.dto.ProfileDTO;
import com.operis.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping
    public ProfileDTO createOrUpdateProfile(@RequestBody ProfileDTO profileDTO) {
        return profileService.createOrUpdateProfile(profileDTO);
    }
}
