package com.oocl.parkingsmart.service;

import com.oocl.parkingsmart.entity.Employee;
import com.oocl.parkingsmart.entity.Order;
import com.oocl.parkingsmart.entity.ParkingLot;
import com.oocl.parkingsmart.exception.NotEnoughCapacityException;
import com.oocl.parkingsmart.repository.OrderRepository;
import com.oocl.parkingsmart.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ParkingLotRepository parkingLotRepository;

    public static final int PAGE_SIZE = 10;

    public Long getAllOrdersNum() {
        return orderRepository.count();
    }

    public List<Order> getPageOrders(int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, PAGE_SIZE);
        return orderRepository.findAll(pageRequest).getContent();
    }

    public Order addOrder(Order order) {
        Order order1 = orderRepository.save(order);
        return order1;
    }

    public List<Order> getAllNewOrders() {
        return orderRepository.getNewOrders();
    }

    public void grabOrderById(Long id, Employee employee) {
        Order order = orderRepository.findById(id).get();
        order.setEmployeeId(employee.getId());
        order.setStatus(1);
        orderRepository.save(order);
    }

    public void updateOrderParkingLot(Long id, ParkingLot parkingLot) throws NotEnoughCapacityException{
        Order order = orderRepository.findById(id).get();
        ParkingLot targetParkingLot=parkingLotRepository.findById(parkingLot.getId()).get();
        if(targetParkingLot.getSize()>targetParkingLot.getParkedNum()){
            targetParkingLot.setParkedNum(targetParkingLot.getParkedNum()+1);
            order.setParkingLotId(parkingLot.getId());
            orderRepository.save(order);
        }else{
            throw new NotEnoughCapacityException("车位已不足");
        }
    }

    public void finishOrder(Long id) {
        Order order = orderRepository.findById(id).get();
        if(order.getParkingLotId()!=null){
            order.setStatus(2);
            ParkingLot parkingLot=parkingLotRepository.findById(order.getParkingLotId()).get();
            parkingLot.setParkedNum(parkingLot.getParkedNum()-1);
            parkingLotRepository.saveAndFlush(parkingLot);
            orderRepository.save(order);
        }

    }
}
