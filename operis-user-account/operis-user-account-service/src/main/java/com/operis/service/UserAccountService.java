package com.operis.service;

import com.operis.dto.UserAccountDTO;
import com.operis.mapper.UserAccountMapper;
import com.operis.model.UserAccount;
import com.operis.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserAccountMapper userAccountMapper;

    public UserAccountDTO createUser(UserAccountDTO userAccountDTO) {
        UserAccount user = userAccountMapper.toUserAccount(userAccountDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userAccountRepository.save(user);
        return userAccountMapper.toUserAccountDTO(user);
    }

    public UserAccountDTO findById(Long id) {
        return userAccountRepository.findById(id).map(user -> userAccountMapper.toUserAccountDTO(user)).orElse(null);
    }
}
