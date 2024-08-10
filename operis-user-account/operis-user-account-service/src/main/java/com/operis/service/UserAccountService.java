package com.operis.service;

import com.operis.dto.UserAccountDTO;
import com.operis.mapper.UserAccountMapper;
import com.operis.model.User;
import com.operis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserAccountMapper userAccountMapper;

    public UserAccountDTO createUser(UserAccountDTO userAccountDTO) {
        User user = userAccountMapper.toUser(userAccountDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        return userAccountMapper.toUserDTO(user);
    }

    public UserAccountDTO findById(Long id) {
        return userRepository.findById(id).map(user -> userAccountMapper.toUserDTO(user)).orElse(null);
    }
}
