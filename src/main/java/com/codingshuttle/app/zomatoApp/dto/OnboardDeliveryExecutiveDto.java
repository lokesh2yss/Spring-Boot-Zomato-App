package com.codingshuttle.app.zomatoApp.dto;

import com.codingshuttle.app.zomatoApp.entities.enums.AvailableStatus;
import com.codingshuttle.app.zomatoApp.entities.enums.VerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnboardDeliveryExecutiveDto {
    private AvailableStatus availableStatus;

    private VerificationStatus verificationStatus;
}
