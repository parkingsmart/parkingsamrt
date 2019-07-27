package com.oocl.parkingsmart.controller;

import com.oocl.parkingsmart.entity.Employee;
import com.oocl.parkingsmart.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@CrossOrigin("*")
@RestController
@RequestMapping("/employees")
public class LoginController {
    private final Logger log = Logger.getLogger(this.getClass().getName());
    @Autowired
    private LoginService loginService;

    @PostMapping()
    public ResponseEntity loginAuthentication(@RequestParam(required = true) String username,@RequestParam(required = true) String password) {
        loginService.loginAuthentication(username,password);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
