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
    public ResponseEntity getAllOrders() {
        HashMap ordersMap = new HashMap();
        List<Order> orders = orderService.getAllOrders();
        ordersMap.put("AllOrdersNum", orders.size());
        ordersMap.put("orders", orders);
        return ResponseEntity.ok().body(ordersMap);
    }

    @GetMapping(params = {"page"})
    public ResponseEntity getPageOrders(@RequestParam(name = "page" ,defaultValue = "1") int page) {
        HashMap ordersMap = new HashMap();
        Long allOrdersNum = orderService.getAllOrdersNum();
        ordersMap.put("AllOrdersNum", allOrdersNum);
        List<Order> orders = orderService.getPageOrders(page);
        ordersMap.put("orders", orders);
        return ResponseEntity.ok().body(ordersMap);
    }

    @GetMapping(params = {"status"})
    public ResponseEntity getOrdersByStatus(@RequestParam(name = "status", defaultValue = "0") int status){
        HashMap ordersMap = new HashMap();
        List<Order> newOrders=orderService.getOrdersByStatus(status);
        ordersMap.put("orders", newOrders);
        return ResponseEntity.ok().body(ordersMap);
    }

    @GetMapping(params = {"status", "page"})
    public ResponseEntity getPageOrdersByStatus(
            @RequestParam(name = "status", defaultValue = "0") int status,
            @RequestParam(name = "page", defaultValue = "1") int page){
        HashMap ordersMap = new HashMap();
        List<Order> newOrders=orderService.getPageOrdersByStatus(status, page);
        ordersMap.put("orders", newOrders);
        return ResponseEntity.ok().body(ordersMap);
    }

    @PostMapping
    public ResponseEntity receiveAnOrder(@RequestBody Order order){
        orderService.addOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).build();
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
