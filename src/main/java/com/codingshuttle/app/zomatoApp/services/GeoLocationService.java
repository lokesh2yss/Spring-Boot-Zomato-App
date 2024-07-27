package com.codingshuttle.app.zomatoApp.services;

import com.codingshuttle.app.zomatoApp.dto.PointDto;
import org.locationtech.jts.geom.Point;

public interface GeoLocationService {
    PointDto getIpApiGeolocation(String ip);

    PointDto getOpenCageGeolocation(String address);
    PointDto getGoogleGeolocation(String address);
}
