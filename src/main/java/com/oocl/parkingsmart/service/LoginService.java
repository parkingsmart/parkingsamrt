package com.oocl.parkingsmart.service;
import com.oocl.parkingsmart.entity.Employee;
import com.oocl.parkingsmart.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class LoginService {
    @Autowired
    private EmployeeRepository employeeRepository;
    public void loginAuthentication(String username,String password) {

    }
}
