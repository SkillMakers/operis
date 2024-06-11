package com.operis.service;

import com.operis.dto.ProfileDTO;
import com.operis.mapper.ProfileMapper;
import com.operis.model.Profile;
import com.operis.model.User;
import com.operis.repository.ProfileRepository;
import com.operis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileMapper profileMapper;

    public ProfileDTO createOrUpdateProfile(ProfileDTO profileDTO) {
        Optional<User> optionalUser = userRepository.findById(profileDTO.getUserId());
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        Profile profile = profileMapper.toEntity(profileDTO);
        profile.setUser(optionalUser.get());

        Profile savedProfile = profileRepository.save(profile);
        return profileMapper.toDto(savedProfile);
    }
}
