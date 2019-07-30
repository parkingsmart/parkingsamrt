package com.oocl.parkingsmart.service;

import com.oocl.parkingsmart.entity.Order;
import com.oocl.parkingsmart.entity.User;
import com.oocl.parkingsmart.exception.AuthenticateFailedException;
import com.oocl.parkingsmart.repository.OrderRepository;
import com.oocl.parkingsmart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
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

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByPhone(username);
//        if (user == null) {
//            throw new UsernameNotFoundException(username);
//        }
//        return new org.springframework.security.core.userdetails.User(username, user.getPassword(),
//                user.getAuthorities().stream()
//                        .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
//                        .collect(Collectors.toList()));
//    }
    public User registered(String username, String password) {
        return userRepository.saveAndFlush(new User(username, password));
    }
}
