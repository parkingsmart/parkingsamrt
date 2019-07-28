package com.oocl.parkingsmart.controller;

import com.oocl.parkingsmart.entity.Employee;
import com.oocl.parkingsmart.entity.ParkingLot;
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
        Employee res = loginService.loginAuthentication(username, password);
        if (res == null) {
            throw new NotEmployeeException();
        }
        res.setPassword("");
        return ResponseEntity.ok().body(res);
    }

    @PostMapping
    public ResponseEntity add(@RequestBody Employee employee) throws ResourceConflictException {
        employeeService.add(employee);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity getAll() {
        List<Employee> employees= employeeService.getAll();
        return ResponseEntity.ok(employees);
    }

    @GetMapping(params = {"page"})
    public ResponseEntity fetchByPage(@RequestParam("page") int page, @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(employeeService.fetchByPage(page, pageSize));
    }

    @GetMapping(path = "/{id}/parking-lots")
    public ResponseEntity fetchParkingLotsById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(employeeService.fetchParkingLotsById(id));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity updateCareer(@PathVariable Long id,@RequestBody Employee employee) {
        Employee result = employeeService.updateCareer(id, employee);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping(path = "/{id}/parking-lots/appointments")
    public ResponseEntity updateParkingLotsManager(@PathVariable("id") Long id, @RequestBody List<Long> ids) {
        employeeService.updateParkingLotsManager(id, ids);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/{id}/parking-lots/leaving")
    public ResponseEntity updateParkingLotsManagerWithNull(@PathVariable("id") Long id, @RequestBody List<Long> ids) {
        employeeService.updateParkingLotsManager(null, ids);
        return ResponseEntity.ok().build();
    }
}




