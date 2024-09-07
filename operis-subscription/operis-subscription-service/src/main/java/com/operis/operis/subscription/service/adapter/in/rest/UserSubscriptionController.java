package com.operis.operis.subscription.service.adapter.in.rest;

import com.operis.operis.subscription.service.adapter.in.rest.model.CreateUserSubscriptionPayloadRecord;
import com.operis.subscription.code.model.port.in.UserSubscriptionUseCases;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        userSubscriptionUseCases.unsubscribe(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
