package com.codingshuttle.app.zomatoApp.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodingResponse {
    private List<Result> results;
    private String status;

    // Getters and setters
    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result {
        private List<AddressComponent> addressComponents;
        private String formattedAddress;
        private Geometry geometry;

        // Getters and setters
        @JsonProperty("address_components")
        public List<AddressComponent> getAddressComponents() {
            return addressComponents;
        }

        public void setAddressComponents(List<AddressComponent> addressComponents) {
            this.addressComponents = addressComponents;
        }

        @JsonProperty("formatted_address")
        public String getFormattedAddress() {
            return formattedAddress;
        }

        public void setFormattedAddress(String formattedAddress) {
            this.formattedAddress = formattedAddress;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class AddressComponent {
            private String longName;
            private String shortName;
            private List<String> types;

            // Getters and setters
            @JsonProperty("long_name")
            public String getLongName() {
                return longName;
            }

            public void setLongName(String longName) {
                this.longName = longName;
            }

            @JsonProperty("short_name")
            public String getShortName() {
                return shortName;
            }

            public void setShortName(String shortName) {
                this.shortName = shortName;
            }

            public List<String> getTypes() {
                return types;
            }

            public void setTypes(List<String> types) {
                this.types = types;
            }
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Geometry {
            private Location location;

            // Getters and setters
            public Location getLocation() {
                return location;
            }

            public void setLocation(Location location) {
                this.location = location;
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Location {
                private double lat;
                private double lng;

                // Getters and setters
                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }
            }
        }
    }
}

