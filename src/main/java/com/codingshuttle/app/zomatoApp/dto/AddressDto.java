package com.codingshuttle.app.zomatoApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private String address1;

    private String address2;

    private String city;

    private String state;

    private Integer pinCode;

    private String landmark;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private PointDto location;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(address1 != null && !address1.isEmpty()) {
            sb.append(address1);
        }
        if(address2 != null && !address2.isEmpty()) {
            sb.append(", ").append(address2);
        }
        if(city != null && !city.isEmpty()) {
            sb.append(", ").append(city);
        }
        if(state != null && !state.isEmpty()) {
            sb.append(", ").append(state);
        }
        if(pinCode != null) {
            sb.append(", ").append(pinCode);
        }
        if(landmark != null && !landmark.isEmpty()) {
            sb.append(", landmark - ").append(address2);
        }
        return sb.toString();
    }
}
