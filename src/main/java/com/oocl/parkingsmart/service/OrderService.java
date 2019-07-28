package com.oocl.parkingsmart.service;

import com.oocl.parkingsmart.entity.Order;
import com.oocl.parkingsmart.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public static final int PAGE_SIZE = 10;

    public int getAllOrdersNum(){
        return orderRepository.findAll().size();
    }

    public List<Order> getPageOrders(int page){
        PageRequest pageRequest = PageRequest.of(page-1,PAGE_SIZE);
        return orderRepository.findAll(pageRequest).getContent();
    }

    public Order addOrder(Order order){
        Order order1 = orderRepository.save(order);
        return order1;
    }

}
