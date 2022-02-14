package com.Sg.companyemployeecasestudy.repository;

import com.Sg.companyemployeecasestudy.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    Employee findByEmployeeId(Integer employeeId);
}
