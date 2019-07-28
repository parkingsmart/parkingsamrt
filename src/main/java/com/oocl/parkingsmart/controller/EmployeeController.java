package com.oocl.parkingsmart.controller;

import com.oocl.parkingsmart.entity.Employee;
import com.oocl.parkingsmart.exception.NotEmployeeException;
import com.oocl.parkingsmart.exception.ResourceConflictException;
import com.oocl.parkingsmart.service.EmployeeService;
import com.oocl.parkingsmart.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@CrossOrigin("*")
@RestController
@RequestMapping("/users")
public class EmployeeController {
    private final Logger log = Logger.getLogger(this.getClass().getName());
    @Autowired
    private LoginService loginService;
    @Autowired
    private EmployeeService employeeService;

    @PostMapping(params = {"username","password"})
    public ResponseEntity loginAuthentication(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) throws NotEmployeeException {
        boolean res = loginService.loginAuthentication(username, password);
        if (!res) {
            throw new NotEmployeeException();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity add(@RequestBody Employee employee) throws ResourceConflictException {
        employeeService.add(employee);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity getAll() {
        List<Employee> orders = employeeService.getAll();
        return ResponseEntity.ok(orders);
    }
}




