package com.operis.controller;

import com.operis.dto.UserAccountDTO;
import com.operis.service.UserAccountService;
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

    @PostMapping("/create")
    public ResponseEntity<UserAccountDTO> createUser(@Valid @RequestBody UserAccountDTO userAccountDTO) {
        UserAccountDTO createdUser = userAccountService.createUser(userAccountDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAccountDTO> findUserById(@PathVariable Long id) {
        UserAccountDTO userAccountDTO = userAccountService.findById(id);
        return new ResponseEntity<>(userAccountDTO, HttpStatus.OK);
    }
}
