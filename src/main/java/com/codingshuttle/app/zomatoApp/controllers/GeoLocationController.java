package com.codingshuttle.app.zomatoApp.controllers;

import com.codingshuttle.app.zomatoApp.dto.PointDto;
import com.codingshuttle.app.zomatoApp.services.GeoLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GeoLocationController {

    private final GeoLocationService geoLocationService;

    @GetMapping("/getLocationFromAddress")
    public PointDto getOpenCageGeolocation(@RequestParam String address) {
        return geoLocationService.getOpenCageGeolocation(address);
    }

    @GetMapping("/getLocationFromIp")
    public PointDto getIpApiGeolocation(@RequestParam String ip) {
        return geoLocationService.getIpApiGeolocation(ip);
    }
}
