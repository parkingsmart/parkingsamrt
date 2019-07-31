package com.oocl.parkingsmart.service;

import com.oocl.parkingsmart.entity.Employee;
import com.oocl.parkingsmart.entity.Order;
import com.oocl.parkingsmart.entity.ParkingLot;
import com.oocl.parkingsmart.exception.NotEnoughCapacityException;
import com.oocl.parkingsmart.exception.ResourceConflictException;
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

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public Long getAllOrdersNum() {
        return orderRepository.count();
    }

    public List<Order> getPageOrders(int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, PAGE_SIZE);
        return orderRepository.findAll(pageRequest).getContent();
    }

    public Order addOrder(Order order) throws ResourceConflictException {
        List<Order> orders = orderRepository.findAll();
        for(Order o : orders) {
            if(o.getCarNumber().equals(order.getCarNumber()) && !o.getUserId().equals(order.getUserId())){
                throw new ResourceConflictException("车牌号已被别人使用！！");
            }
            if(o.getCarNumber().equals(order.getCarNumber()) && o.getStatus() != 4) {
                throw new ResourceConflictException("该车辆订单正在进行中！");
            }
        }
        Order order1 = orderRepository.save(order);
        return order1;
    }

    public List<Order> getOrdersByStatus(int status) {
        return orderRepository.findAllByStatus(status);
    }

    public List<Order> getPageOrdersByStatus(int status, int page){
        return orderRepository.findByStatus(status, new PageRequest(page - 1, PAGE_SIZE)).getContent();
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


    public void updateOrderStatus(Long id, int status) {
        Order order = orderRepository.findById(id).get();
        if(order.getParkingLotId()!=null){
            order.setStatus(status);
            if (status == 4){
                order.setEndAt(System.currentTimeMillis());
            }else {
                ParkingLot parkingLot=parkingLotRepository.findById(order.getParkingLotId()).get();
                parkingLot.setParkedNum(parkingLot.getParkedNum()-1);
                parkingLotRepository.saveAndFlush(parkingLot);
            }
            orderRepository.save(order);
        }
    }

    public Order getOrdersById(Long id) {
        return orderRepository.findById(id).get();
    }


    public void payAnOrder(Long id, Long endTime) {
        Order order = orderRepository.findById(id).get();
        order.setStatus(4);
        order.setEndAt(endTime);
        orderRepository.save(order);
    }
}
