package com.operis.user.account.service;

import com.operis.user.account.domain.UserAccount;
import com.operis.user.account.mapper.UserAccountMapper;
import com.operis.user.account.repository.UserAccountEntity;
import com.operis.user.account.repository.UserAccountRepository;
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

    public UserAccount createUser(UserAccount userAccountDTO) {
        UserAccountEntity user = userAccountMapper.toUserAccountEntity(userAccountDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userAccountRepository.save(user);
        return userAccountMapper.toUserAccountEntity(user);
    }

    public UserAccount findById(Long id) {
        return userAccountRepository.findById(id).map(user -> userAccountMapper.toUserAccountEntity(user)).orElse(null);
    }
}
