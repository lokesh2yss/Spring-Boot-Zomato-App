package com.codingshuttle.app.zomatoApp.repositories;

import com.codingshuttle.app.zomatoApp.entities.Customer;
import com.codingshuttle.app.zomatoApp.entities.OrderRequest;
import com.codingshuttle.app.zomatoApp.entities.enums.OrderRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRequestRepository extends JpaRepository<OrderRequest, Long> {
    Optional<OrderRequest> findByCustomerAndOrderRequestStatus(Customer customer, OrderRequestStatus orderRequestStatus);
}
