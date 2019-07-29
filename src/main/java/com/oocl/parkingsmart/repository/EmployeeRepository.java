package com.oocl.parkingsmart.repository;

import com.oocl.parkingsmart.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Employee findByEmail(String username);

    Employee findByPhone(String username);

    Page<Employee> findAllByOfficeId(Pageable pageable, int officeId);
}
