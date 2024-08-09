package com.codingshuttle.app.zomatoApp.services.impl;

import com.codingshuttle.app.zomatoApp.dto.PointDto;
import com.codingshuttle.app.zomatoApp.json.GoogleMapGeocodingResponse;
import com.codingshuttle.app.zomatoApp.json.IpApiGeocodingResponseDto;
import com.codingshuttle.app.zomatoApp.json.OpenCageGeocodingResponseDto;
import com.codingshuttle.app.zomatoApp.services.GeoLocationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;

@Service
public class GeoLocationServiceImpl implements GeoLocationService {
    @Value("${GeoLocationFromIPService.base.url}")
    public String GEO_FROM_IP_BASE_URL;

    @Value("${GeoLocationFromAddressService.base.url}")
    public String GEO_FROM_ADDRESS_BASE_URL;
    Logger log = LoggerFactory.getLogger(GeoLocationServiceImpl.class);
    private final RestClient googleMapRestClient;
    private final RestClient openCageRestClient;
    private final RestClient ipApiRestClient;

    public GeoLocationServiceImpl(
            @Qualifier("googleMapRestClient") RestClient googleMapRestClient,
            @Qualifier("ipApiRestClient") RestClient ipApiRestClient,
            @Qualifier("openCageRestClient") RestClient openCageRestClient
    ) {
        this.googleMapRestClient = googleMapRestClient;
        this.ipApiRestClient = ipApiRestClient;
        this.openCageRestClient = openCageRestClient;
    }

    @Value("${GoogleMap.api.key}")
    private String GOOGLE_MAP_API_KEY;

    @Value("${OpenCage.api.key}")
    private String OPEN_CAGE_API_KEY;
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public PointDto getIpApiGeolocation(String ip) {
        String url = GEO_FROM_IP_BASE_URL+"/" + ip;
        IpApiGeocodingResponseDto ipApiGeocodingResponseDto;
        try {
            ipApiGeocodingResponseDto = ipApiRestClient.get()
                    .uri(url)
                    .retrieve()
                    .body(IpApiGeocodingResponseDto.class);
            double latitude = 0.0;
            double longitude = 0.0;
            try {
                latitude = ipApiGeocodingResponseDto.getLat();
                longitude = ipApiGeocodingResponseDto.getLon();
            }catch (Exception e) {
                log.error(e.getMessage(), (Object) e.getStackTrace());
                throw new RuntimeException(e);
            }
            return getPointFromLatLong(latitude, longitude);

        }catch (Exception e) {
            log.error(e.getMessage(), (Object) e.getStackTrace());
            throw new RuntimeException(e);
        }
    }

    @Override
    public PointDto getOpenCageGeolocation(String address) {
        String encodedAddress = encodeURIComponent(address);
        String url = String.format(GEO_FROM_ADDRESS_BASE_URL, encodedAddress, OPEN_CAGE_API_KEY);

        OpenCageGeocodingResponseDto jsonResponse = null;
        try {
            jsonResponse = openCageRestClient.get()
                    .uri(url)
                    .retrieve()
                    .body(OpenCageGeocodingResponseDto.class);

            double lat = jsonResponse.getResults().get(0).getGeometry().getLat();
            double lng = jsonResponse.getResults().get(0).getGeometry().getLng();
            return getPointFromLatLong(lat, lng);
        }catch(Exception e) {
            log.error(e.getMessage(), (Object) e.getStackTrace());
            throw new RuntimeException(e);
        }
    }

    private String encodeURIComponent(String value) {
        try {
            return java.net.URLEncoder.encode(value, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error(e.getMessage());
            return value;
        }
    }
    @Override
    public PointDto getGoogleGeolocation(String address) {
        String url = "/geocode/json?address=" + address + "&key="+GOOGLE_MAP_API_KEY;
        String jsonResponse = "";
        try {
            jsonResponse = googleMapRestClient.get()
                    .uri(url)
                    .retrieve()
                    .body(String.class);
        }catch(Exception e) {
            log.error(e.getMessage(), (Object) e.getStackTrace());
            throw new RuntimeException(e);
        }
        GoogleMapGeocodingResponse googleMapGeocodingResponse;
        try {
            googleMapGeocodingResponse = objectMapper.readValue(jsonResponse, GoogleMapGeocodingResponse.class);
        }catch (Exception e) {
            log.error(e.getMessage(), (Object) e.getStackTrace());
            throw new RuntimeException(e);
        }
        double latitude;
        double longitude;
        try {
            latitude = googleMapGeocodingResponse.getResults().getFirst().getGeometry().getLocation().getLat();
            longitude = googleMapGeocodingResponse.getResults().getFirst().getGeometry().getLocation().getLng();
        }catch (Exception e) {
            log.error(e.getMessage(), (Object) e.getStackTrace());
            throw new RuntimeException(e);
        }
        return getPointFromLatLong(latitude, longitude);
    }
    private PointDto getPointFromLatLong(double latitude, double longitude) {
        double[] coordinates = {longitude, latitude};
        return new PointDto(coordinates);
    }
}
