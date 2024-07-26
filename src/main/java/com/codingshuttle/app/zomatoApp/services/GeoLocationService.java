package com.codingshuttle.app.zomatoApp.services;

import org.locationtech.jts.geom.Point;

public interface GeoLocationService {
    Point getIpApiGeolocation(String ip);

    Point getGoogleGeolocation(String address);
}
