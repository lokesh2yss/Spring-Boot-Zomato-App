package com.codingshuttle.app.zomatoApp.configs;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
public class RestClientConfig {
    @Value("${GeoLocationFromIPService.base.url}")
    private String GEO_FROM_IP_BASE_URL;

    @Value("${GeoLocationFromAddressService.base.url}")
    private String GEO_FROM_ADDRESS_BASE_URL;
    @Bean
    @Qualifier("ipApiRestClient")
    public RestClient getIpApiRestClient() {
        return RestClient.builder()
                .baseUrl(GEO_FROM_IP_BASE_URL)
                .defaultStatusHandler(HttpStatusCode::is5xxServerError, (req, res) -> {
                    throw new RuntimeException("Server Error Occurred");
                })
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    @Qualifier("googleMapRestClient")
    public RestClient getGoogleMapRestClient() {
        return RestClient.builder()
                .baseUrl(GEO_FROM_ADDRESS_BASE_URL)
                .defaultStatusHandler(HttpStatusCode::is5xxServerError, (req, res) -> {
                    throw new RuntimeException("Server Error Occurred");
                })
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    @Qualifier("openCageRestClient")
    public RestClient getOpenCageRestClient() {
        return RestClient.builder()
                .baseUrl(GEO_FROM_ADDRESS_BASE_URL)
                .defaultStatusHandler(HttpStatusCode::is5xxServerError, (req, res) -> {
                    throw new RuntimeException("Server Error Occurred");
                })
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .build();
    }
}

