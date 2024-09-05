package com.codingshuttle.app.zomatoApp.services;

import com.codingshuttle.app.zomatoApp.dto.AddressDto;
import com.codingshuttle.app.zomatoApp.dto.PointDto;
import com.codingshuttle.app.zomatoApp.entities.DeliveryExecutive;
import com.codingshuttle.app.zomatoApp.entities.Order;

public interface DeliveryExecutiveService {
    Order acceptOrderDelivery(Order order);

    Order pickupOrderForDelivery(Order order, String pickupOtp);

    Order completeOrderDelivery(Order order, String deliveryOtp);
    PointDto getDeliveryExecutiveLiveLocation(Long orderId);
    AddressDto addDeliveryExecutiveAddress(Long deliveryExecutiveId, AddressDto addressDto);

    AddressDto updateDeliveryExecutiveAddress(Long deliveryExecutiveId, Long addressId, AddressDto addressDto);

    boolean deleteDeliveryExecutiveAddress(Long deliveryExecutiveId, Long addressId);

    AddressDto getDeliveryExecutiveDefaultAddress(Long  deliveryExecutiveId);

    AddressDto setDeliveryExecutiveDefaultAddress(Long  deliveryExecutiveId, Long addressId);

    DeliveryExecutive createNewDeliveryExecutive(DeliveryExecutive deliveryExecutive);
}
