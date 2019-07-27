package com.oocl.parkingsmart.controller;

import com.oocl.parkingsmart.entity.Employee;
import com.oocl.parkingsmart.exception.NotEmployeeException;
import com.oocl.parkingsmart.exception.SystemException;
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
    public ResponseEntity loginAuthentication(@RequestParam(name = "username") String username,@RequestParam(name = "password") String password) throws NotEmployeeException {
        Employee employee = loginService.loginAuthentication(username,password);
        if (employee == null){
            throw new NotEmployeeException();
        }
        return ResponseEntity.ok().build();
    }
}
