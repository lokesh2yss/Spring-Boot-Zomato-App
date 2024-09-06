package com.codingshuttle.app.zomatoApp.controllers;

import com.codingshuttle.app.zomatoApp.dto.PointDto;
import com.codingshuttle.app.zomatoApp.services.GeoLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GeoLocationController {

    private final GeoLocationService geoLocationService;

    @GetMapping("/getLocationFromAddress")
    public ResponseEntity<PointDto> getOpenCageGeolocation(@RequestParam String address) {
        return ResponseEntity.ok(geoLocationService.getOpenCageGeolocation(address));
    }

    @GetMapping("/getLocationFromIp")
    public ResponseEntity<PointDto> getIpApiGeolocation(@RequestParam String ip) {
        return ResponseEntity.ok(geoLocationService.getIpApiGeolocation(ip));
    }
}
