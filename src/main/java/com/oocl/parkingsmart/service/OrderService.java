package com.oocl.parkingsmart.service;

import com.oocl.parkingsmart.endpoint.UserEndpoint;
import com.oocl.parkingsmart.entity.Employee;
import com.oocl.parkingsmart.entity.Order;
import com.oocl.parkingsmart.entity.ParkingLot;
import com.oocl.parkingsmart.entity.ParkingPromotions;
import com.oocl.parkingsmart.exception.NotEnoughCapacityException;
import com.oocl.parkingsmart.exception.ResourceConflictException;
import com.oocl.parkingsmart.repository.OrderRepository;
import com.oocl.parkingsmart.repository.ParkingLotRepository;
import com.oocl.parkingsmart.repository.ParkingPromotionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ParkingLotRepository parkingLotRepository;

    @Autowired
    private ParkingPromotionsRepository parkingPromotionsRepository;

    @Autowired
    private UserEndpoint userEndpoint;
    public static final int PAGE_SIZE = 10;
    public static final int PRICE_PER_HOUR = 10;

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
            if(o.getCarNumber().equals(order.getCarNumber()) && o.getStatus() < 5) {
                if(!o.getUserId().equals(order.getUserId())){
                    throw new ResourceConflictException("车牌号已被别人使用！！");
                }
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

    public void grabOrderById(Long id, Long employeeId) {
        sendWebSocketData(1);
        Order order = orderRepository.findById(id).get();
        order.setEmployeeId(employeeId);
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


    public void updateOrderStatus(Long id, int status) throws ResourceConflictException{
        sendWebSocketData(status);
        Order order = orderRepository.findById(id).get();
        if(order.getParkingLotId()!=null){
            order.setStatus(status);
            if (status == 5){
                order.setEndAt(System.currentTimeMillis());
                Double amount = getAllAmount(order);
                order.setAmount(amount);
            }else if (status == 3){
                ParkingLot parkingLot = parkingLotRepository.findById(order.getParkingLotId()).get();
                parkingLot.setParkedNum(parkingLot.getParkedNum() - 1);
                parkingLotRepository.saveAndFlush(parkingLot);
            }
            orderRepository.save(order);
        }else {
            throw new ResourceConflictException("稍等，工作人员正在为您分配停车场");
        }
    }

    private void sendWebSocketData(int status) {
         if(status == 1){
             userEndpoint.sendAllMessage("您的订单已被接收，请等待小哥联系。");
         }else if(status == 3){
             userEndpoint.sendAllMessage("您的车辆已被停放好，请放心。");
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

    public double getAllAmount(Order order){
        Long diffTime = order.getEndAt() - order.getCreateAt();
        double minutesDiff =  (double)diffTime / (60 * 1000);
        int hours = (int)(minutesDiff / 60);
        int minutes = (int)minutesDiff % 60;
        double amount = minutes == 0 ? hours * PRICE_PER_HOUR : (hours + 1) * PRICE_PER_HOUR;
        return amount;
    }

    public void finishOrder(Long orderId, Long promotionId) {
        Order order = orderRepository.findById(orderId).get();
        order.setStatus(6);
        if (promotionId == -1){
            order.setDiscountAmount(order.getAmount());
        }else {
            order.setDiscountAmount(getDiscountAmount(promotionId, order.getAmount()));
            order.setPromotionId(promotionId);
        }
        orderRepository.saveAndFlush(order);
    }

    public Double getDiscountAmount(Long promotionId, Double pay) {
        ParkingPromotions parkingPromotions = parkingPromotionsRepository.findById(promotionId).get();
        Integer type = parkingPromotions.getType();
        Double amount = parkingPromotions.getAmount();
        DecimalFormat df = new DecimalFormat("0.0");
        Double discountAmount = 0.0;
        if (type == 0) {
            discountAmount = pay * amount;
        } else if (type == 1) {
            discountAmount = pay - amount;
        }
        return Double.parseDouble(df.format(discountAmount));
    }

}
