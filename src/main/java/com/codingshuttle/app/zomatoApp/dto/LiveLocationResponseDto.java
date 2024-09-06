package com.codingshuttle.app.zomatoApp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LiveLocationResponseDto {
    private Long orderId;
    private Long deliveryExecutiveId;

    private PointDto liveLocation;
}
