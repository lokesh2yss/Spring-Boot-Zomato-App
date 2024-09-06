package com.codingshuttle.app.zomatoApp.controllers;

import com.codingshuttle.app.zomatoApp.dto.LiveLocationResponseDto;
import com.codingshuttle.app.zomatoApp.services.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deliveries")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;

    @GetMapping(path = "/{orderId}/location")
    public ResponseEntity<LiveLocationResponseDto> getDeliveryExecutiveLiveLocation(@PathVariable Long orderId) {
        return ResponseEntity.ok(deliveryService.getDeliveryExecutiveLiveLocation(orderId));
    }
}
