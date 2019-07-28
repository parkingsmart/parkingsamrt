package com.oocl.parkingsmart.repository;

import com.oocl.parkingsmart.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
