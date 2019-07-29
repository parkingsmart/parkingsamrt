package com.oocl.parkingsmart.controller;

import com.oocl.parkingsmart.entity.Employee;
import com.oocl.parkingsmart.entity.Order;
import com.oocl.parkingsmart.entity.User;
import com.oocl.parkingsmart.exception.AuthenticateFailedException;
import com.oocl.parkingsmart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity registered(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password){
        User res = userService.registered(username, password);
        return ResponseEntity.ok().body(res);
    }
    @GetMapping("/{id}")
    public ResponseEntity getAllUserOrders(@PathVariable Long id){
        List<Order> orders =userService.getAllUserOrders(id);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }
    @PutMapping(path = "/{id}")
    public ResponseEntity putUserOrder(@PathVariable Long id,@RequestBody Long oderID,@RequestBody String msg){
        Order order = userService.fetchACar(id,oderID,msg);
        return ResponseEntity.status(HttpStatus.OK).body(order);

    }
}
