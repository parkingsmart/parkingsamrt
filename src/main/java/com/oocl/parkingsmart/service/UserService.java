package com.oocl.parkingsmart.service;

import com.oocl.parkingsmart.entity.Order;
import com.oocl.parkingsmart.entity.User;
import com.oocl.parkingsmart.exception.AuthenticateFailedException;
import com.oocl.parkingsmart.exception.PasswordValidException;
import com.oocl.parkingsmart.exception.ResourceNotFoundException;
import com.oocl.parkingsmart.repository.OrderRepository;
import com.oocl.parkingsmart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllUserOrders(Long id) {
        List<Order> orderList = orderRepository.findAll();
        List<Order> resultorderList = orderList.stream().filter(order -> order.getUserId().equals(id)).collect(Collectors.toList());
        return resultorderList;
    }

    public Order fetchACar(Long id, Long oderID) {
        Order order = orderRepository.findById(oderID).get();
        order.setStatus(3);
        Order savedOrder = orderRepository.save(order);
        return savedOrder;
    }

    public User loginAuthentication(String username, String password) throws AuthenticateFailedException {
        User user = userRepository.findByPhone(username);
        if (user == null || !user.getPassword().equals(password)) {
            throw new AuthenticateFailedException();
        }
        user.setPassword("");
        return user;
    }

    public User registered(String username, String password) {
        return userRepository.saveAndFlush(new User(username, password));
    }

    public User updatePassword(Long id, String oldPassword,String newPassword) throws PasswordValidException, ResourceNotFoundException {
        User user = null;
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            user = optionalUser.get();
            if(!user.getPassword().equals(oldPassword.trim())) {
                throw new PasswordValidException();
            }
            user.setPassword(newPassword);
            return userRepository.saveAndFlush(user);
        }
        throw new ResourceNotFoundException();
    }

    public List<String> getAllUserCarNums(Long id) {
        List<String> carNums = orderRepository.findAllCarNums(id);
        return carNums;

    }

    public void updateIntegral(Long id, Long orderId) {
        User user = userRepository.findById(id).get();
        user.setIntegral(user.getIntegral()-5);
        userRepository.saveAndFlush(user);
        Order order = orderRepository.findById(orderId).get();
        order.setStatus(5);
        orderRepository.saveAndFlush(order);
    }

    public User addPayPassword(Long id, String payPassword) {
        User user = userRepository.findById(id).get();
        user.setPayPassword(payPassword);
        return userRepository.saveAndFlush(user);
    }

    public User getUserInfoById(Long id) {
        System.out.println(id);
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.isPresent()?optionalUser.get():null;
    }
}
