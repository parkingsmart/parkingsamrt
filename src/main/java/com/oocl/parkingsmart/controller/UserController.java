package com.oocl.parkingsmart.controller;

import com.oocl.parkingsmart.entity.Order;
import com.oocl.parkingsmart.entity.ShopPromotions;
import com.oocl.parkingsmart.entity.User;
import com.oocl.parkingsmart.exception.AuthenticateFailedException;
import com.oocl.parkingsmart.exception.PasswordValidException;
import com.oocl.parkingsmart.exception.PayPasswordException;
import com.oocl.parkingsmart.exception.PromotionIsNotExistException;
import com.oocl.parkingsmart.exception.ResourceNotFoundException;
import com.oocl.parkingsmart.service.OrderService;
import com.oocl.parkingsmart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RequestMapping("/users")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) throws AuthenticateFailedException {
        User res = userService.loginAuthentication(username, password);
        return ResponseEntity.ok().body(res);
    }

    @PostMapping("/registered")
    public ResponseEntity registered(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
        User res = userService.registered(username, password);
        return ResponseEntity.ok().body(res);
    }

    @GetMapping(value = "/{id}",params = {"msg"})
    public ResponseEntity getAllUserOrders(@PathVariable Long id, @RequestParam(required = false, defaultValue = "all") String msg) {
        if ("carNums".equals(msg)) {
            List<String> carNums = userService.getAllUserCarNums(id);
            return ResponseEntity.status(HttpStatus.OK).body(carNums);
        } else {
            List<Order> orders = userService.getAllUserOrders(id);
            return ResponseEntity.status(HttpStatus.OK).body(orders);
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity putUserOrder(@PathVariable Long id, @RequestParam(name = "orderID") Long oderID) {
        Order order = userService.fetchACar(id, oderID);
        return ResponseEntity.status(HttpStatus.OK).body(order);

    }

    @PutMapping(path = "/{id}", params = {"orderId", "status"})
    public ResponseEntity updateUserOrderStatue(@PathVariable Long id, @RequestParam(name = "orderId") Long orderID, @RequestParam(name = "status") Integer status) {
        orderService.updateOrderStatus(orderID, status);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(path = "/{id}",params = {"oldPassword","newPassword"})
    public ResponseEntity updateUserInfo(@PathVariable Long id,@RequestParam(name = "oldPassword") String oldPassword,@RequestParam(name = "newPassword") String newPassword) throws PasswordValidException, ResourceNotFoundException {
        User user = userService.updatePassword(id,oldPassword,newPassword);
        return ResponseEntity.status(HttpStatus.OK).body(user);

    }

    @GetMapping("/{id}")
    public ResponseEntity getUserInfoById(@PathVariable Long id){
        User user = userService.getUserInfoById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping(params = {"phone"})
    public ResponseEntity getUserInfoByPhone(@RequestParam("phone") String phone){
        User user = userService.getUserByPhone(phone);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PatchMapping(path = "/{id}",params = {"orderId"})
    public ResponseEntity updateUserInfo(@PathVariable Long id,@RequestParam(name = "orderId") Long orderId) {
        userService.updateIntegral(id, orderId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(path = "/{id}",params = {"payPassword"})
    public ResponseEntity addPayPassword(@PathVariable Long id,@RequestParam(name = "payPassword") String payPassword) throws PayPasswordException {
        User user = userService.addPayPassword(id,payPassword);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PatchMapping(value = "/{id}",params = {"orderId", "promotionId"})
    public ResponseEntity finishOrder(@PathVariable Long id,
                                      @RequestParam(name = "orderId") Long orderId,
                                      @RequestParam(name = "promotionId") Long promotionId
    ){
        userService.finishOrder(id, orderId, promotionId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/promotions")
    public ResponseEntity getUserPromotionById(@PathVariable Long id) {
        List<ShopPromotions> shopPromotions = userService.getUserPromotionById(id);
        return ResponseEntity.status(HttpStatus.OK).body(shopPromotions);
    }
}
