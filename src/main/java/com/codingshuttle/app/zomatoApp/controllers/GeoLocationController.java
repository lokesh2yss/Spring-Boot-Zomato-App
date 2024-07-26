package com.codingshuttle.app.zomatoApp.controllers;

import com.codingshuttle.app.zomatoApp.services.GeoLocationService;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GeoLocationController {

    private final GeoLocationService geoLocationService;

    @GetMapping("/get-google-geolocation")
    public Point getGoogleGeolocation(@RequestParam String address) {
        return geoLocationService.getGoogleGeolocation(address);
    }

    @GetMapping("/get-ipapi-geolocation")
    public Point getIpApiGeolocation(@RequestParam String ip) {
        return geoLocationService.getIpApiGeolocation(ip);
    }
}
