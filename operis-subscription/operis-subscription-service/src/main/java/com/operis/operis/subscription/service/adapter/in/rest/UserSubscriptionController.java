package com.operis.operis.subscription.service.adapter.in.rest;

import com.operis.operis.subscription.service.adapter.in.rest.helper.JWTTokenService;
import com.operis.operis.subscription.service.adapter.in.rest.model.CreateUserSubscriptionPayloadRecord;
import com.operis.operis.subscription.service.adapter.in.rest.model.GetUserSubscriptionPayload;
import com.operis.operis.subscription.service.adapter.in.rest.model.UserSubscriptionDto;
import com.operis.subscription.code.model.UserSubscription;
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
    private final JWTTokenService jwtTokenService;

    @PostMapping
    public ResponseEntity<Void> subscribe(@RequestBody CreateUserSubscriptionPayloadRecord payload,
                                          @RequestHeader("Authorization") String authorizationHeader) {
        String connectedUserEmail = jwtTokenService.extractUserEmail(authorizationHeader);
        userSubscriptionUseCases.subscribeUser(payload.toCommand(connectedUserEmail));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/unsubscribe")
    public ResponseEntity<Void> delete(@RequestHeader("Authorization") String authorizationHeader) {
        String connectedUserEmail = jwtTokenService.extractUserEmail(authorizationHeader);
        userSubscriptionUseCases.unsubscribe(connectedUserEmail);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/get")
    public ResponseEntity<UserSubscriptionDto> get(@RequestBody GetUserSubscriptionPayload payload) {
        UserSubscription userSubscription = userSubscriptionUseCases.get(payload.toCommand());
        UserSubscriptionDto result = UserSubscriptionDto.from(userSubscription);
        return ResponseEntity.ok(result);
    }
}
