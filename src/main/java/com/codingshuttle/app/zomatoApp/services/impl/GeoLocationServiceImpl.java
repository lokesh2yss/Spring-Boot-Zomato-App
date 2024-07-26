package com.codingshuttle.app.zomatoApp.services.impl;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.codingshuttle.app.zomatoApp.dto.PointDto;
import com.codingshuttle.app.zomatoApp.json.GeocodingResponse;
import com.codingshuttle.app.zomatoApp.services.GeoLocationService;
import com.codingshuttle.app.zomatoApp.utils.GeometryUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class GeoLocationServiceImpl implements GeoLocationService {
    private final RestClient googleMapRestClient;
    private final RestClient ipApiRestClient;

    public GeoLocationServiceImpl(@Qualifier("googleMapRestClient") RestClient googleMapRestClient, @Qualifier("ipApiRestClient") RestClient ipApiRestClient) {
        this.googleMapRestClient = googleMapRestClient;
        this.ipApiRestClient = ipApiRestClient;
    }

    @Value("${GoogleMap.api.key}")
    private String GOOGLE_MAP_API_KEY;
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public Point getIpApiGeolocation(String ip) {
        String url = "/" + ip;
        String jsonResponse =  ipApiRestClient.get()
                .uri(url)
                .retrieve()
                .body(String.class);
        return null;
    }

    @Override
    public Point getGoogleGeolocation(String address) {
        String url = "/geocode/json?address=" + address + "&key="+GOOGLE_MAP_API_KEY;
        String jsonResponse = "";
        try {
            jsonResponse = googleMapRestClient.get()
                    .uri(url)
                    .retrieve()
                    .body(String.class);
        }catch(Exception e) {
            throw new RuntimeException(e);
        }
        GeocodingResponse geocodingResponse;
        try {
            geocodingResponse = objectMapper.readValue(jsonResponse, GeocodingResponse.class);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        double latitude;
        double longitude;
        try {
            latitude = geocodingResponse.getResults().getFirst().getGeometry().getLocation().getLat();
            longitude = geocodingResponse.getResults().getFirst().getGeometry().getLocation().getLng();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        double[] coordinates = {longitude, latitude};
        PointDto pointDto = new PointDto(coordinates);

        return GeometryUtil.createPoint(pointDto);
    }
}
