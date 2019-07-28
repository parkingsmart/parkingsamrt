package com.oocl.parkingsmart.controller;

import com.oocl.parkingsmart.entity.Order;
import com.oocl.parkingsmart.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@CrossOrigin
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping
    public ResponseEntity getAllValidOrders(@RequestParam(required = false, defaultValue = "1")int page) {
        HashMap ordersMap = new HashMap();
        int allOrdersNum = orderService.getAllOrdersNum();
        ordersMap.put("AllOrdersNum", allOrdersNum);
        List<Order> orders = orderService.getPageOrders(page);
        ordersMap.put("pageOrders", orders);
        return ResponseEntity.ok().body(ordersMap);
    }

    @PostMapping
    public ResponseEntity receiveAnOrder(@RequestBody Order order){
        orderService.addOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
