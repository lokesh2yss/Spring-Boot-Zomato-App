package com.codingshuttle.app.zomatoApp.repositories;

import com.codingshuttle.app.zomatoApp.entities.DeliveryExecutive;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryExecutiveRepository extends JpaRepository<DeliveryExecutive, Long> {
    @Query(value = "SELECT d.*, ST_Distance(d.current_location, :restaurantLocation) AS distance " +
            "FROM delivery_executives d " +
            "WHERE d.available_status=AVAILABLE " +
            "AND ST_DWithin(d.current_location, :restaurantLocation, 10000) " +
            "ORDER BY distance ASC " +
            "LIMIT 10", nativeQuery = true)
    List<DeliveryExecutive> findTenNearestDeliveryExecutives(Point restaurantLocation);

    @Query(value = "SELECT d.* " +
            "FROM delivery_executives d " +
            "WHERE d.available_status=AVAILABLE " +
            "AND ST_DWithin(d.current_location, :restaurantLocation, 15000) " +
            "ORDER BY d.rating DESC " +
            "LIMIT 10", nativeQuery = true)
    List<DeliveryExecutive> findTenNearbyTopRatedDeliveryExecutives(Point restaurantLocation);
}
