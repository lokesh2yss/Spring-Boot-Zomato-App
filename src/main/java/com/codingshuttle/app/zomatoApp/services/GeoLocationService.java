package com.codingshuttle.app.zomatoApp.services;

import com.codingshuttle.app.zomatoApp.dto.PointDto;

public interface GeoLocationService {
    PointDto getIpApiGeolocation(String ip);

    PointDto getOpenCageGeolocation(String address);
    PointDto getGoogleGeolocation(String address);
}
