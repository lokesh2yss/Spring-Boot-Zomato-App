package com.codingshuttle.app.zomatoApp.services;

import com.codingshuttle.app.zomatoApp.dto.AddressDto;
import com.codingshuttle.app.zomatoApp.dto.PointDto;

public interface DeliveryExecutiveService {
    PointDto getDeliveryExecutiveLiveLocation(Long orderId);
    AddressDto addDeliveryExecutiveAddress(Long deliveryExecutiveId, AddressDto addressDto);

    AddressDto updateDeliveryExecutiveAddress(Long deliveryExecutiveId, Long addressId, AddressDto addressDto);

    boolean deleteDeliveryExecutiveAddress(Long deliveryExecutiveId, Long addressId);

    AddressDto getDeliveryExecutiveDefaultAddress(Long  deliveryExecutiveId);

    AddressDto setDeliveryExecutiveDefaultAddress(Long  deliveryExecutiveId, Long addressId);
}
