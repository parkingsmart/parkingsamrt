package com.oocl.parkingsmart.service;
import com.oocl.parkingsmart.entity.Employee;
import com.oocl.parkingsmart.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class LoginService {
    @Autowired
    private EmployeeRepository employeeRepository;
    public Employee loginAuthentication(String username,String password) {
        Employee employee = null;
        if(username.indexOf("@") > 0){
            employee = employeeRepository.findByEmail(username);
        }else if(username.length() == 11){
            employee = employeeRepository.findByPhone(username);
        }else {
            employee = employeeRepository.findById(Long.parseLong(username)).get();
        }
        return employee;
    }
}
