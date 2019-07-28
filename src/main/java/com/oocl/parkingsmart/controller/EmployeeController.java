package com.oocl.parkingsmart.controller;

import com.oocl.parkingsmart.entity.Employee;
import com.oocl.parkingsmart.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/users")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity add(@RequestBody Employee employee) {
        employeeService.add(employee);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity getAll() {
        List<Employee> orders = employeeService.getAll();
        return ResponseEntity.ok(orders);
    }

}
