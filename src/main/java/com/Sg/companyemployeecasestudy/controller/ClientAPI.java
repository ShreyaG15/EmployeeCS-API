package com.Sg.companyemployeecasestudy.controller;

import com.Sg.companyemployeecasestudy.service.CompanyService;
import com.Sg.companyemployeecasestudy.service.EmployeeService;
import openapi.api.ClientApiApi;
import openapi.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/employee-service")
public class ClientAPI implements ClientApiApi {

    CompanyService companyService;
    EmployeeService employeeService;

    @Autowired
    public ClientAPI(CompanyService companyService, EmployeeService employeeService) {
        this.companyService = companyService;
        this.employeeService = employeeService;
    }

    @Override
    public ResponseEntity<CreateCompanyResponseDto> postCompany(CreateCompanyRequestDto createCompanyRequestDto) {
        return companyService.createCompany(createCompanyRequestDto);
    }

    @Override
    public ResponseEntity<GetCompanyResponseDto> getCompany(String companyShortCode) {
        return companyService.getCompany(companyShortCode);
    }

    @Override
    public ResponseEntity<CreateEmployeeResponseDto> postEmployee(CreateEmployeeRequestDto createEmployeeRequestDto) {
        try {
            return employeeService.createEmployee(createEmployeeRequestDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<GetEmployeeResponseDto> getEmployee(BigDecimal employeeId) {
        return employeeService.getEmployee(employeeId);
    }
}
