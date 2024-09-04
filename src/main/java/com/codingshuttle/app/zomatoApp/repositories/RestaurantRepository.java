package com.codingshuttle.app.zomatoApp.repositories;

import com.codingshuttle.app.zomatoApp.entities.Restaurant;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query(value = "SELECT r.*, ST_Distance(r.location, :customerLocation) AS distance " +
            "FROM restaurants r " +
            "WHERE r.opening_status=OPEN " +
            "AND r.opening_time < CAST(NOW() AS TIME) " +
            "AND r.closing_time > CAST(NOW() AS TIME) " +
            "AND ST_DWithin(r.location, :customerLocation, 10000) " +
            "ORDER BY distance ASC " +
            "LIMIT 20", nativeQuery = true)
    List<Restaurant> findTwentyNearestRestaurants(Point customerLocation);

    @Query(value = "SELECT r.* " +
            "FROM restaurants r " +
            "WHERE r.opening_status=OPEN " +
            "AND r.opening_time < CAST(NOW() AS TIME) " +
            "AND r.closing_time > CAST(NOW() AS TIME) " +
            "AND ST_DWithin(r.location, :customerLocation, 15000) " +
            "ORDER BY r.rating DESC " +
            "LIMIT 20", nativeQuery = true)
    List<Restaurant> findTwentyNearbyTopRatedRestaurants(Point customerLocation);
}
