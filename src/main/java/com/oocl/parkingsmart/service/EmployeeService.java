package com.oocl.parkingsmart.service;

import com.oocl.parkingsmart.entity.Employee;
import com.oocl.parkingsmart.repository.EmployeeRepository;
import com.oocl.parkingsmart.utils.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.SplittableRandom;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public void add(Employee employee) {
        String password =NumberUtil.createPwd(8);
        employee.setPassword(password);
        employeeRepository.save(employee);
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }
}
