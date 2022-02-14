package com.Sg.companyemployeecasestudy.validation;

import com.Sg.companyemployeecasestudy.entity.Employee;
import com.Sg.companyemployeecasestudy.exception.EmployeeNotFound;
import com.Sg.companyemployeecasestudy.exception.FieldCantBeNullOrEmpty;
import com.Sg.companyemployeecasestudy.exception.MandatoryFieldRequired;
import com.Sg.companyemployeecasestudy.repository.EmployeeRepository;
import openapi.model.CreateEmployeeRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class EmployeeValidation {

    EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeValidation(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee validateEmployeeByEmployeeId(BigDecimal employeeId) {
        if (Objects.isNull(employeeId) || employeeId.intValue() == 0) {
            throw new MandatoryFieldRequired();
        }
        Employee employee = employeeRepository.findByEmployeeId(employeeId.intValue());
        if (Objects.isNull(employee)) {
            throw new EmployeeNotFound();
        }
        return employee;
    }

    public void validateEmployee(CreateEmployeeRequestDto createEmployeeRequestDto) {
        if (Objects.isNull(createEmployeeRequestDto) ||
                createEmployeeRequestDto.getCompanyId().isEmpty() ||
                createEmployeeRequestDto.getFirstName().isEmpty() ||
                createEmployeeRequestDto.getLastName().isEmpty() ||
                Objects.isNull(createEmployeeRequestDto.getDateOfBirth()) ||
                createEmployeeRequestDto.getDepartment().isEmpty()) {
            throw new FieldCantBeNullOrEmpty();
        }
    }
}
