package com.oocl.parkingsmart.service;

import com.oocl.parkingsmart.entity.Employee;
import com.oocl.parkingsmart.repository.EmployeeRepository;
import com.oocl.parkingsmart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if ("admin".equals(username)) {
            Collection<SimpleGrantedAuthority> collection = new HashSet<>();
            collection.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            String password = passwordEncoder.encode("admin");

            return new User("admin", password, collection);
        }

        UserDetails userDetails = loadUserByPhone(username);
        userDetails = userDetails == null ? loadEmployeeById(username) : userDetails;

        if (userDetails == null) {
            throw new UsernameNotFoundException(username);
        }
        return userDetails;
    }

    private UserDetails loadEmployeeById(String id) {
        Optional<Employee> res = employeeRepository.findById(Long.parseLong(id));

        if (!res.isPresent()) {
            throw new UsernameNotFoundException(id);
        }
        Employee employee = res.get();
        Collection<SimpleGrantedAuthority> collection = new HashSet<>();
        collection.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));

        return new User(id, employee.getPassword(), collection);
    }

    private UserDetails loadUserByPhone(String phone) throws UsernameNotFoundException {
        com.oocl.parkingsmart.entity.User user = userRepository.findByPhone(phone);
        if (user == null) {
            return null;
        }
        Collection<SimpleGrantedAuthority> collection = new HashSet<>();
        collection.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new User(phone, user.getPassword(), collection);
    }
}
