package com.oocl.parkingsmart.service;

import com.oocl.parkingsmart.entity.Order;
import com.oocl.parkingsmart.entity.ParkingPromotions;
import com.oocl.parkingsmart.entity.ShopPromotions;
import com.oocl.parkingsmart.entity.User;
import com.oocl.parkingsmart.entity.UserShopPromotions;
import com.oocl.parkingsmart.exception.*;
import com.oocl.parkingsmart.repository.OrderRepository;
import com.oocl.parkingsmart.repository.ParkingPromotionsRepository;
import com.oocl.parkingsmart.repository.ShopRepository;
import com.oocl.parkingsmart.repository.UserRepository;
import com.oocl.parkingsmart.repository.UserShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private UserShopRepository userShopRepository;

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public List<Order> getAllUserOrders(Long id) {
        Sort sort = new Sort(Sort.Direction.DESC,"createAt");
        List<Order> orderList = orderRepository.findAll(sort);
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
        password = passwordEncoder().encode(password);
        return userRepository.saveAndFlush(new User(username, password));
    }

    public User updatePassword(Long id, String oldPassword,String newPassword) throws PasswordValidException, ResourceNotFoundException {
        User user = null;
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isPresent()){
            user = optionalUser.get();
            if(!passwordEncoder().matches(oldPassword,user.getPassword())) {
                throw new PasswordValidException();
            }
            user.setPassword(passwordEncoder().encode(newPassword));
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

    public User addPayPassword(Long id, String payPassword) throws PayPasswordException {
        if(payPassword.length()!=6){
            throw new PayPasswordException();
        }
        User user = userRepository.findById(id).get();
        user.setPayPassword(payPassword);
        return userRepository.saveAndFlush(user);
    }

    public User getUserInfoById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.isPresent()?optionalUser.get():null;
    }

    public void finishOrder(Long id, Long orderId, Long promotionId) {
        User user = userRepository.findById(id).get();
        if (promotionId == -1){
            user.setIntegral(user.getIntegral() + 5);
        }else {
            user.setIntegral(user.getIntegral() - 15);
        }
        userRepository.saveAndFlush(user);
        orderService.finishOrder(orderId, promotionId);
    }

    public List<ShopPromotions> getUserPromotionById(Long id) {
        List<ShopPromotions> shopPromotions = new ArrayList<>();
        List<UserShopPromotions> userShopPromotions = userShopRepository.findAllByUserId(id);
        userShopPromotions.stream().filter(x->x.getActive()).forEach(x->addActivePromotion(x,shopPromotions));
        return shopPromotions;
    }

    private void addActivePromotion(UserShopPromotions userShopPromotions,List<ShopPromotions> shopPromotions){
        Optional<ShopPromotions> shopPromotionsOptional = shopRepository.findById(userShopPromotions.getShopId());
        if(shopPromotionsOptional.isPresent()){
            shopPromotions.add(shopPromotionsOptional.get());
        }
    }

    public ShopPromotions addPromotionById(Long id, ShopPromotions shop) throws UnsupportedEncodingException, UserNotFoundException, InsufficientPointsException {
        long startTime = System.currentTimeMillis()/1000;
        long endTime = startTime+60*60*24*7;
        long redemptionCode = (System.currentTimeMillis()+(System.currentTimeMillis()+1000*60*60*24*7))/10;
        String shopMallName = new String(shop.getShopMallName().getBytes("UTF-8"),"UTF-8");
        ShopPromotions shopPromotions = new ShopPromotions(startTime,endTime,shop.getType(),shopMallName,shop.getAmount(),redemptionCode);
        final ShopPromotions shopPromotions1 = shopRepository.saveAndFlush(shopPromotions);
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            if(user.getIntegral() < 20){
                throw new InsufficientPointsException();
            }else {
                user.setIntegral(user.getIntegral() - 20);
                userRepository.saveAndFlush(user);
            }
        }else{
            throw new UserNotFoundException();
        }
        userShopRepository.saveAndFlush(new UserShopPromotions(id,shopPromotions1.getId()));
        return shopPromotions1;
    }
    public User getUserByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }
}
