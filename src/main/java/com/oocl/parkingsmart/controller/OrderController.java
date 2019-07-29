package com.oocl.parkingsmart.controller;

import com.oocl.parkingsmart.entity.Employee;
import com.oocl.parkingsmart.entity.Order;
import com.oocl.parkingsmart.entity.ParkingLot;
import com.oocl.parkingsmart.exception.NotEnoughCapacityException;
import com.oocl.parkingsmart.exception.ResourceConflictException;
import com.oocl.parkingsmart.exception.ResourceNotFoundException;
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
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping
    public ResponseEntity getAllOrders(@RequestParam(required = false, defaultValue = "1")int page) {
        HashMap ordersMap = new HashMap();
        Long allOrdersNum = orderService.getAllOrdersNum();
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

    @GetMapping("/newOrders")
    public ResponseEntity getNewOrders(){
        HashMap ordersMap = new HashMap();
        List<Order> newOrders=orderService.getAllNewOrders();
        ordersMap.put("newOrders", newOrders);
        return ResponseEntity.ok().body(ordersMap);
    }
    @PutMapping("/newOrders/{id}")
    public ResponseEntity getNewOrders(@PathVariable Long id,@RequestBody Employee employee) {
        orderService.grabOrderById(id,employee);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/newOrders/{id}/parkinglot")
    public ResponseEntity updateOrderParkingLot(@PathVariable Long id,@RequestBody ParkingLot parkingLot) throws NotEnoughCapacityException {
        orderService.updateOrderParkingLot(id,parkingLot);
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/newOrders/{id}")
    public ResponseEntity finishOrder(@PathVariable Long id){
        orderService.finishOrder(id);
        return ResponseEntity.ok().build();
    }
}
