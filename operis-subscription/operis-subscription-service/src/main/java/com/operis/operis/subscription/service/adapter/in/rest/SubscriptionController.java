package com.operis.operis.subscription.service.adapter.in.rest;

import com.operis.operis.subscription.service.adapter.in.rest.model.CreateSubscriptionPayloadRecord;
import com.operis.operis.subscription.service.adapter.in.rest.model.SubscriptionDto;
import com.operis.subscription.code.model.port.in.SubscriptionUseCases;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionUseCases subscriptionUseCases;

    @PostMapping()
    public ResponseEntity<SubscriptionDto> create(@RequestBody CreateSubscriptionPayloadRecord payload) {
        SubscriptionDto result = SubscriptionDto.from(subscriptionUseCases.addSubscription(payload.toCommand()));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping
    public List<SubscriptionDto> getAllSubscriptions() {
        return subscriptionUseCases.getAllSubscriptions().stream()
                .map(SubscriptionDto::from)
                .toList();
    }
}
