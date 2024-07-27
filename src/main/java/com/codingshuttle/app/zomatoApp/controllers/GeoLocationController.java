package com.codingshuttle.app.zomatoApp.controllers;

import com.codingshuttle.app.zomatoApp.dto.PointDto;
import com.codingshuttle.app.zomatoApp.services.GeoLocationService;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
public class GeoLocationController {

    private final GeoLocationService geoLocationService;

    @GetMapping("/get-google-geolocation")
    public PointDto getOpenCageGeolocation(@RequestParam String address) {
        return geoLocationService.getOpenCageGeolocation(address);
    }

    @GetMapping("/get-ipapi-geolocation")
    public PointDto getIpApiGeolocation(@RequestParam String ip) {
        return geoLocationService.getIpApiGeolocation(ip);
    }
}
