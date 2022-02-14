package com.Sg.companyemployeecasestudy.repository;

import com.Sg.companyemployeecasestudy.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    Employee findByEmployeeId(Integer employeeId);
}
