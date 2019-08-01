package com.oocl.parkingsmart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @RequestMapping("/login")
    public ResponseEntity loginPage() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
