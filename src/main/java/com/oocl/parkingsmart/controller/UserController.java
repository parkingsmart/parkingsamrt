package com.oocl.parkingsmart.controller;

import com.oocl.parkingsmart.entity.Employee;
import com.oocl.parkingsmart.entity.Order;
import com.oocl.parkingsmart.entity.User;
import com.oocl.parkingsmart.exception.AuthenticateFailedException;
import com.oocl.parkingsmart.exception.PasswordValidException;
import com.oocl.parkingsmart.exception.ResourceNotFoundException;
import com.oocl.parkingsmart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Array;
import java.util.List;

@CrossOrigin("*")
@RequestMapping("/api/users")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

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

    @GetMapping("/{id}")
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
    public ResponseEntity putUserOrder(@PathVariable Long id, @RequestParam(name = "oderID") Long oderID) {
        Order order = userService.fetchACar(id, oderID);
        return ResponseEntity.status(HttpStatus.OK).body(order);

    }
    @PutMapping(path = "/{id}",params = {"oldPassword","newPassword"})
    public ResponseEntity updateUserInfo(@PathVariable Long id,@RequestParam(name = "oldPassword") String oldPassword,@RequestParam(name = "newPassword") String newPassword) throws PasswordValidException, ResourceNotFoundException {
        User user = userService.updatePassword(id,oldPassword,newPassword);
        return ResponseEntity.status(HttpStatus.OK).body(user);

    }
}
