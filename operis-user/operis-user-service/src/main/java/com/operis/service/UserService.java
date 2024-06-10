package com.operis.service;

import com.operis.dto.UserDTO;
import com.operis.mapper.UserMapper;
import com.operis.model.User;
import com.operis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toUser(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        return userMapper.toUserDTO(user);
    }

    public UserDTO findById(Long id) {
        return userRepository.findById(id).map(user -> userMapper.toUserDTO(user)).orElse(null);
    }
}
