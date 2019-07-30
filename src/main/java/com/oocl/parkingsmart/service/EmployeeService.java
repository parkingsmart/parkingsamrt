package com.oocl.parkingsmart.service;

import com.oocl.parkingsmart.entity.Employee;
import com.oocl.parkingsmart.entity.Order;
import com.oocl.parkingsmart.entity.ParkingLot;
import com.oocl.parkingsmart.entity.User;
import com.oocl.parkingsmart.exception.ResourceConflictException;
import com.oocl.parkingsmart.repository.EmployeeRepository;
import com.oocl.parkingsmart.repository.OrderRepository;
import com.oocl.parkingsmart.repository.ParkingLotRepository;
import com.oocl.parkingsmart.utils.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.SplittableRandom;
import java.util.stream.Collectors;


@Service
public class EmployeeService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Employee loginAuthentication(String username,String password) {
        Employee employee = null;
        if(username.indexOf("@") > 0){
            employee = employeeRepository.findByEmail(username);
            return employee!=null?((employee.getPassword().equals(password))?employee:null):null;
        }else if(username.length() == 11){
            employee = employeeRepository.findByPhone(username);
            return employee!=null?((employee.getPassword().equals(password))?employee:null):null;
        }else {
            Optional<Employee> res = employeeRepository.findById(Long.parseLong(username));
            if(res.isPresent()){
                employee = res.get();
                return (employee.getPassword().equals(password))?employee:null;
            }
            return null;
        }
    }
    public void add(Employee employee) throws ResourceConflictException {
        String password = NumberUtil.createPwd(8);
        employee.setPassword(password);

        List<Employee> employeeList = employeeRepository.findAll();
        for (Employee el : employeeList) {
            if (employee.getEmail().equals(el.getEmail())) {
                throw new ResourceConflictException("邮箱已存在");
            }
            if (employee.getPhone().equals(el.getPhone())) {
                throw new ResourceConflictException("手机号已存在");
            }
        }
        employeeRepository.saveAndFlush(employee);

    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Page<Employee> fetchByPage(int page, int pageSzie) {
        return employeeRepository.findAll(PageRequest.of(page - 1, pageSzie));
    }

    public List<ParkingLot> fetchParkingLotsById(Long id) {
        return parkingLotRepository.findAllByManager(id);
    }

    public Employee updateCareer(Long id, Employee employee) {
        Employee updateEmployee = employeeRepository.findById(id).get();
        updateEmployee.setOfficeId(employee.getOfficeId());
        return employeeRepository.saveAndFlush(updateEmployee);
    }

    public void updateParkingLotsManager(Long id, List<Long> ids) {
        parkingLotRepository.updateManagerByIds(id, ids);
    }

    public Page<Employee> fetchByPageAndOfficeId(int page, int pageSize, Integer officeId) {
        return employeeRepository.findAllByOfficeId(PageRequest.of(page - 1, pageSize), officeId.intValue());
    }

    public List<Order> getOnGoingOrdersById(Long id) {

        List<Order> orderList = orderRepository.findAllByEmployeeId(id);
        orderList = orderList.stream().filter(item -> (item.getStatus() != 0 && item.getStatus() != 4)).collect(Collectors.toList());
        return  orderList;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee user = findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(),
                simpleGrantedAuthorities);
    }

    private Employee findByUserName(String username){
        Employee employee = null;
        if(username.indexOf("@") > 0){
            employee = employeeRepository.findByEmail(username);
            return employee;
        }else if(username.length() == 11){
            employee = employeeRepository.findByPhone(username);
            return employee;
        }else {
            Optional<Employee> res = employeeRepository.findById(Long.parseLong(username));
            if(res.isPresent()){
                employee = res.get();
                return employee;
            }
            return null;
        }
    }
}
