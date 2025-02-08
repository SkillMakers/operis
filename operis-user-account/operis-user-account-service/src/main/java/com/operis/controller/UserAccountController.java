package com.operis.controller;

import com.operis.dto.UserAccountDTO;
import com.operis.mapper.UserAccountDtoMapper;
import com.operis.user.account.service.UserAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private UserAccountDtoMapper userAccountDtoMapper;

    @PostMapping("/create")
    public ResponseEntity<UserAccountDTO> createUser(@Valid @RequestBody UserAccountDTO userAccountDTO) {
        var createdUserAccount = userAccountService.createUser(userAccountDtoMapper.toUserAccount(userAccountDTO));
        var createdUserAccountDTO = userAccountDtoMapper.toUserAccountDTO(createdUserAccount);
        return new ResponseEntity<>(createdUserAccountDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAccountDTO> findUserById(@PathVariable Long id) {
        var foundUserAccount = userAccountService.findById(id);
        var userAccountDTO = userAccountDtoMapper.toUserAccountDTO(foundUserAccount);
        return new ResponseEntity<>(userAccountDTO, HttpStatus.OK);
    }
}
