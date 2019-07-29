package com.oocl.parkingsmart.repository;

import com.oocl.parkingsmart.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByStatus(int status);

    Page<Order> findByStatus(int status, Pageable Pageable);


}
