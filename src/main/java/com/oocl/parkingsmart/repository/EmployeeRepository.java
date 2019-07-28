package com.oocl.parkingsmart.repository;

import com.oocl.parkingsmart.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
