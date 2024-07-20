package com.codingshuttle.app.zomatoApp.dto;

import com.codingshuttle.app.zomatoApp.entities.enums.AvailableStatus;
import com.codingshuttle.app.zomatoApp.entities.enums.VerificationStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryExecutiveDto {
    private UserDto user;

    private Point currentLocation;

    private Double rating;

    private AvailableStatus availableStatus;

    private VerificationStatus verificationStatus;
}
