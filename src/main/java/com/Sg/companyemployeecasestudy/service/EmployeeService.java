package com.Sg.companyemployeecasestudy.service;

import com.Sg.companyemployeecasestudy.entity.Employee;
import com.Sg.companyemployeecasestudy.repository.CompanyRepository;
import com.Sg.companyemployeecasestudy.repository.EmployeeRepository;
import com.Sg.companyemployeecasestudy.validation.EmployeeValidation;
import openapi.model.CreateCompanyResponseDto;
import openapi.model.CreateEmployeeRequestDto;
import openapi.model.CreateEmployeeResponseDto;
import openapi.model.GetEmployeeResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class EmployeeService {

    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeValidation employeeValidation;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, EmployeeValidation employeeValidation, CompanyRepository companyRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeValidation = employeeValidation;
        this.companyRepository = companyRepository;
    }

    public ResponseEntity<CreateEmployeeResponseDto> createEmployee(CreateEmployeeRequestDto createEmployeeRequestDto) {
        employeeValidation.validateEmployee(createEmployeeRequestDto);
        Employee employee = new Employee();
        employee.setFirstName(createEmployeeRequestDto.getFirstName());
        employee.setLastName(createEmployeeRequestDto.getLastName());
        employee.setDob(createEmployeeRequestDto.getDateOfBirth());
        employee.setJoiningDate(createEmployeeRequestDto.getJoiningDate());
        employee.setAge(createEmployeeRequestDto.getAge().intValue());
        employee.setDepartment(createEmployeeRequestDto.getDepartment());
        employee.setCreatedBy("System");
        employee.setCreatedOn(LocalDateTime.now());
        employee.setUpdatedBy("System");
        employee.setUpdatedOn(LocalDateTime.now());
        if (companyRepository.findById(createEmployeeRequestDto.getCompanyId()).isPresent()){
            employee.setCompany(companyRepository.findById(createEmployeeRequestDto.getCompanyId()).get());
        }
        Employee savedEmployee = employeeRepository.save(employee);
        CreateEmployeeResponseDto createEmployeeResponseDto = new CreateEmployeeResponseDto();
        createEmployeeResponseDto.setEmployeeId(BigDecimal.valueOf(savedEmployee.getEmployeeId()));
        ResponseEntity<CreateEmployeeResponseDto> responseEntity = new ResponseEntity<>(createEmployeeResponseDto, HttpStatus.CREATED);
        return responseEntity;

    }

    public ResponseEntity<GetEmployeeResponseDto> getEmployee(BigDecimal employeeId) {
        Employee employee = employeeValidation.validateEmployeeByEmployeeId(employeeId);
        GetEmployeeResponseDto getEmployeeResponseDto = new GetEmployeeResponseDto();
        getEmployeeResponseDto.setEmployeeId(BigDecimal.valueOf(employee.getEmployeeId()));
        getEmployeeResponseDto.setCompanyId(employee.getCompany().getId());
        getEmployeeResponseDto.setFirstName(employee.getFirstName());
        getEmployeeResponseDto.setLastName(employee.getLastName());
        getEmployeeResponseDto.setDateOfBirth(employee.getDob());
        getEmployeeResponseDto.setJoiningDate(employee.getJoiningDate());
        getEmployeeResponseDto.setAge(BigDecimal.valueOf(employee.getAge()));
        getEmployeeResponseDto.setDepartment(employee.getDepartment());
        getEmployeeResponseDto.setCreatedOn(employee.getCreatedOn().atOffset(ZoneOffset.UTC));
        getEmployeeResponseDto.setUpdatedOn(employee.getUpdatedOn().atOffset(ZoneOffset.UTC));
        ResponseEntity<GetEmployeeResponseDto> responseEntity = new ResponseEntity<>(getEmployeeResponseDto,HttpStatus.OK);
        return responseEntity;
    }
}
