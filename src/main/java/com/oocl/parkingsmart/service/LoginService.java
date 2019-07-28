package com.oocl.parkingsmart.service;
import com.oocl.parkingsmart.entity.Employee;
import com.oocl.parkingsmart.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {
    @Autowired
    private EmployeeRepository employeeRepository;
    public boolean loginAuthentication(String username,String password) {
        Employee employee = null;
        if(username.indexOf("@") > 0){
            employee = employeeRepository.findByEmail(username);

            return employee!=null?employee.getPassword().equals(password):false;
        }else if(username.length() == 11){
            employee = employeeRepository.findByPhone(username);
            return employee!=null?employee.getPassword().equals(password):false;
        }else {
            Optional<Employee> res = employeeRepository.findById(Long.parseLong(username));
            if(res.isPresent()){
                employee = res.get();
                return employee!=null?employee.getPassword().equals(password):false;
            }
            return false;
        }
    }


}
