package com.codingshuttle.app.zomatoApp.repositories;

import com.codingshuttle.app.zomatoApp.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByOrder(Order order);

    List<Rating> findByDeliveryExecutive(DeliveryExecutive deliveryExecutive);

    List<Rating> findByCustomer(Customer customer);

    List<Rating> findByRestaurant(Restaurant restaurant);


}
