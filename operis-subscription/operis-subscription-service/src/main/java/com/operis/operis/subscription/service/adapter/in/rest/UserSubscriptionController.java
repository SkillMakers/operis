package com.operis.operis.subscription.service.adapter.in.rest;

import com.operis.operis.subscription.service.adapter.in.rest.model.CreateUserSubscriptionPayloadRecord;
import com.operis.subscription.code.model.port.in.UserSubscriptionUseCases;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-subscriptions")
@RequiredArgsConstructor
public class UserSubscriptionController {

    private final UserSubscriptionUseCases userSubscriptionUseCases;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateUserSubscriptionPayloadRecord payload) {
        userSubscriptionUseCases.subscribeUser(payload.toCommand());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
