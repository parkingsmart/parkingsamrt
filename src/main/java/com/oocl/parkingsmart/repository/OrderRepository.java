package com.oocl.parkingsmart.repository;

import com.oocl.parkingsmart.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "select * from order_list where status=0",nativeQuery = true)
    List<Order> getNewOrders();
}
