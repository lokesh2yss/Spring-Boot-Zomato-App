package com.codingshuttle.app.zomatoApp.externalApiDto;

import lombok.Data;

import java.util.List;

@Data
public class OpenCageGeocodingResponseDto {
    private List<Result> results;
    @Data
    public static class Result {
        private Geometry geometry;
        @Data
        public static class Geometry {
            private double lat;
            private double lng;

        }
    }
}
