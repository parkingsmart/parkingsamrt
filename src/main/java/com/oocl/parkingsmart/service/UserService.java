package com.oocl.parkingsmart.service;

import com.oocl.parkingsmart.entity.Order;
import com.oocl.parkingsmart.repository.OrderRepository;
import com.oocl.parkingsmart.repository.UserRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllUserOrders(Long id) {
        List<Order> orderList = orderRepository.findAll();
        List<Order> resultorderList = orderList.stream().filter(order -> order.getUserId() == id).collect(Collectors.toList());
        return resultorderList;
    }

    public Order fetchACar(Long id, Long oderID,String msg) {
        Order order = orderRepository.findById(oderID).get();
        order.setStatus(3);
        Order savedOrder = orderRepository.save(order);
        return savedOrder;
    }
}
