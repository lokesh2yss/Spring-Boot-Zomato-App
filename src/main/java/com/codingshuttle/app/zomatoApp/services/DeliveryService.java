package com.codingshuttle.app.zomatoApp.services;

import com.codingshuttle.app.zomatoApp.dto.LiveLocationResponseDto;

public interface DeliveryService {
    LiveLocationResponseDto getDeliveryExecutiveLiveLocation(Long orderId);
}
